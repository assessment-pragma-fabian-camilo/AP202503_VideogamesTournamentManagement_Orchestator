package com.fc2o.usecase.tournament.business;

import com.fc2o.model.registration.Registration;
import com.fc2o.model.tournament.Tournament;
import com.fc2o.model.transaction.Status;
import com.fc2o.model.transaction.Transaction;
import com.fc2o.model.transaction.Type;
import com.fc2o.model.user.User;
import com.fc2o.service.ValidateTournamentPermissionsService;
import com.fc2o.usecase.registration.crud.CreateRegistrationUseCase;
import com.fc2o.usecase.tournament.TournamentUseCases;
import com.fc2o.usecase.tournament.crud.PatchTournamentUseCase;
import com.fc2o.usecase.tournament.crud.RetrieveTournamentUseCase;
import com.fc2o.usecase.transaction.crud.CreateTransactionUseCase;
import com.fc2o.usecase.user.crud.RetrieveUserUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class PreRegisterParticipantUseCase {

  private final RetrieveTournamentUseCase retrieveTournamentUseCase;
  private final PatchTournamentUseCase patchTournamentUseCase;
  private final CreateRegistrationUseCase createRegistrationUseCase;
  private final CreateTransactionUseCase createTransactionUseCase;
  private final ValidateTournamentPermissionsService permissionsService;
  private final RetrieveUserUseCase retrieveUserUseCase;

  /*
  * Validar que el participante no esté registrado en este torneo
  * Crear Transacción en STARTED
  * Creat Registro en PENDING
  *
   */

  public Mono<Tournament> preRegisterParticipant(UUID participantId, UUID tournamentId) {
    return retrieveUserUseCase.retrieveById(participantId)
      .filter(User::isActive)
      .switchIfEmpty(Mono.error(new RuntimeException("No puedes participar: Estas baneado en la plataforma")))
      .flatMap(user -> retrieveTournamentUseCase.retrieveById(tournamentId))
      .doOnNext(ticket -> permissionsService.validate(tournamentId, participantId, TournamentUseCases.PREREGISTER_PARTICIPANT))
      .filter(tournament -> tournament.preRegisteredParticipantIds().contains(participantId))
      .switchIfEmpty(Mono.error(new RuntimeException("Ya tienes un pre-registro activo para este torneo")))
      .filter(tournament -> tournament.participantIds().contains(participantId))
      .switchIfEmpty(Mono.error(new RuntimeException("Ya eres un participante activo en este torneo")))
      .filter(Tournament::isInProgress)
      .switchIfEmpty(Mono.error(new RuntimeException("El torneo ya inició")))
      .filter(Tournament::isFinished)
      .switchIfEmpty(Mono.error(new RuntimeException("El torneo ya terminó")))
      .filter(Tournament::isCanceled)
      .switchIfEmpty(Mono.error(new RuntimeException("El torneo fue cancelado")))
      .filter(Tournament::isPaid)
      .map(tournament -> Status.STARTED)
      .switchIfEmpty(Mono.just(Status.APPROVED))
      .doOnNext(status ->
        createTransactionUseCase.create(buildTransaction(participantId, tournamentId, status))
      )
      .doOnNext(status ->
        createRegistrationUseCase.create(buildRegistration(participantId, tournamentId))
      )
      .flatMap(status -> patchTournamentUseCase.patchPreRegisterParticipant(participantId));
  }

  public Mono<Tournament> forcePreRegisterParticipant(UUID participantId, UUID tournamentId, UUID userId) {
    return retrieveUserUseCase.retrieveById(participantId)
      .filter(User::isActive)
      .switchIfEmpty(Mono.error(new RuntimeException("No puedes participar: Estas baneado en la plataforma")))
      .flatMap(user -> retrieveTournamentUseCase.retrieveById(tournamentId))
      .doOnNext(ticket -> permissionsService.validate(tournamentId, participantId, TournamentUseCases.PREREGISTER_PARTICIPANT))
      .doOnNext(ticket -> permissionsService.validate(tournamentId, userId, TournamentUseCases.FORCE_PREREGISTER_PARTICIPANT))
      .filter(tournament -> !tournament.preRegisteredParticipantIds().contains(participantId))
      .switchIfEmpty(Mono.error(new RuntimeException("Ya tienes un pre-registro activo")))
      .filter(tournament -> tournament.participantIds().contains(participantId))
      .switchIfEmpty(Mono.error(new RuntimeException("Ya eres un participante activo")))
      .filter(tournament -> !tournament.disqualifiedParticipantIds().contains(participantId))
      .switchIfEmpty(Mono.error(new RuntimeException("No tienes permitido participar en este torneo")))
      .filter(tournament -> !tournament.isFinished())
      .switchIfEmpty(Mono.error(new RuntimeException("El torneo ya terminó")))
      .filter(tournament -> !tournament.isCanceled())
      .switchIfEmpty(Mono.error(new RuntimeException("El torneo fue cancelado")))
      .filter(Tournament::isPaid)
      .map(tournament -> Status.STARTED)
      .switchIfEmpty(Mono.just(Status.APPROVED))
      .doOnNext(status ->
        createTransactionUseCase.create(buildTransaction(participantId, tournamentId, status))
      )
      .doOnNext(tournament ->
        createRegistrationUseCase.create(buildRegistration(participantId, tournamentId))
      )
      .flatMap(status -> patchTournamentUseCase.patchPreRegisterParticipant(participantId));
  }

  private Transaction buildTransaction(UUID participantId, UUID tournamentId, Status status) {
    return Transaction.builder()
      .id(UUID.randomUUID())
      .type(Type.PARTICIPATION)
      .status(status)
      .customerId(participantId)
      .tournamentId(tournamentId)
      .createdTime(LocalDateTime.now())
      .build();
  }

  private Registration buildRegistration(UUID participantId, UUID tournamentId) {
    return Registration.builder()
      .id(UUID.randomUUID())
      .createdTime(LocalDateTime.now())
      .tournamentId(tournamentId)
      .status(com.fc2o.model.registration.Status.PENDING)
      .participantId(participantId)
      .build();
  }
}

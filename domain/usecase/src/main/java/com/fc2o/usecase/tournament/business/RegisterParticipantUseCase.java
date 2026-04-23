package com.fc2o.usecase.tournament.business;

import com.fc2o.model.registration.Registration;
import com.fc2o.model.ticket.Status;
import com.fc2o.model.ticket.Ticket;
import com.fc2o.model.tournament.Tournament;
import com.fc2o.model.user.User;
import com.fc2o.service.ValidateTournamentPermissionsService;
import com.fc2o.usecase.registration.crud.PatchRegistrationUseCase;
import com.fc2o.usecase.ticket.crud.PatchTicketUseCase;
import com.fc2o.usecase.ticket.crud.RetrieveTicketUseCase;
import com.fc2o.usecase.tournament.TournamentUseCases;
import com.fc2o.usecase.tournament.crud.PatchTournamentUseCase;
import com.fc2o.usecase.tournament.crud.RetrieveTournamentUseCase;
import com.fc2o.usecase.user.crud.RetrieveUserUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
public class RegisterParticipantUseCase {

  private final RetrieveTicketUseCase retrieveTicketUseCase;
  private final PatchTournamentUseCase patchTournamentUseCase;
  private final RetrieveTournamentUseCase retrieveTournamentUseCase;
  private final PatchTicketUseCase patchTicketUseCase;
  private final PatchRegistrationUseCase patchRegistrationUseCase;
  private final ValidateTournamentPermissionsService permissionsService;
  private final RetrieveUserUseCase retrieveUserUseCase;

  /*
  * Validar que el torneo esté en estado NOT_STARTED
  * Validar que exista una transacción válida para este torneo:
  *   - que el tipo de ticket sea para participante
  * Validar que la transacción tenga status APPROVED -> Esto debe estar validado para generar el ticket
  * Validar que el status del ticket no sea USED ni BLOCKED (NEW)
  * Actualizar tournament para quitar al pre-inscrito y ponerlo como participante
  * Actualizar el estado del ticket a USED
  * Crear el registro Registration
   */

  public Mono<Tournament> registerParticipant(UUID ticketId) {
    AtomicReference<UUID> customerId = new AtomicReference<>();
    return retrieveUserUseCase.retrieveById(customerId.get())
      .filter(User::isActive)
      .switchIfEmpty(Mono.error(new RuntimeException("No puedes participar: Estas baneado en la plataforma")))
      .flatMap(user -> retrieveTicketUseCase.retrieveTicketById(ticketId))
      .doOnNext(ticket -> customerId.set(ticket.customerId()))
      .filter(Ticket::isParticipationType)
      .switchIfEmpty(Mono.error(new RuntimeException("El ticket no es para participación")))
      .filter(Ticket::isNew)
      .switchIfEmpty(Mono.error(new RuntimeException("El ticket ya fue utilizado o está bloqueado")))
      .doOnNext(ticket -> permissionsService.validate(ticket.tournamentId(), ticket.customerId(), TournamentUseCases.REGISTER_PARTICIPANT))
      .flatMap(ticket -> retrieveTournamentUseCase.retrieveById(ticket.tournamentId()))
      .filter(tournament -> !tournament.participantIds().contains(customerId.get()))
      .switchIfEmpty(Mono.error(new RuntimeException("Ya eres un participante activo")))
      .filter(tournament -> tournament.preRegisteredParticipantIds().contains(customerId.get()))
      .switchIfEmpty(Mono.error(new RuntimeException("Aún no has realizado el pre-registro")))
      .filter(tournament -> !tournament.disqualifiedParticipantIds().contains(customerId.get()))
      .switchIfEmpty(Mono.error(new RuntimeException("No tienes permitido participar en este torneo")))
      .filter(tournament -> !tournament.isInProgress())
      .switchIfEmpty(Mono.error(new RuntimeException("El torneo ya inició")))
      .filter(tournament -> !tournament.isFinished())
      .switchIfEmpty(Mono.error(new RuntimeException("El torneo ya terminó")))
      .filter(tournament -> !tournament.isCanceled())
      .switchIfEmpty(Mono.error(new RuntimeException("El torneo fue cancelado")))
      .flatMap(tournament ->
        patchTournamentUseCase.patchRegisterParticipant(customerId.get())
      )
      .doOnNext(tournament ->
        patchRegistrationUseCase.patch(Registration.builder().status(com.fc2o.model.registration.Status.APPROVED).build())
      )
      .doOnNext(ticket -> patchTicketUseCase.patch(Ticket.builder().status(Status.USED).build()));
  }

  public Mono<Tournament> forceRegisterParticipant(UUID ticketId, UUID userId) {
    AtomicReference<UUID> customerId = new AtomicReference<>();
    return retrieveUserUseCase.retrieveById(customerId.get())
      .filter(User::isActive)
      .switchIfEmpty(Mono.error(new RuntimeException("No puedes participar: Estas baneado en la plataforma")))
      .flatMap(user -> retrieveTicketUseCase.retrieveTicketById(ticketId))
      .doOnNext(ticket -> customerId.set(ticket.customerId()))
      .filter(Ticket::isParticipationType)
      .switchIfEmpty(Mono.error(new RuntimeException("El ticket no es para participación")))
      .filter(Ticket::isNew)
      .switchIfEmpty(Mono.error(new RuntimeException("El ticket ya fue utilizado o está bloqueado")))
      .doOnNext(ticket -> permissionsService.validate(ticket.tournamentId(), userId, TournamentUseCases.FORCE_REGISTER_PARTICIPANT))
      .doOnNext(ticket -> permissionsService.validate(ticket.tournamentId(), ticket.customerId(), TournamentUseCases.REGISTER_PARTICIPANT))
      .flatMap(ticket -> retrieveTournamentUseCase.retrieveById(ticket.tournamentId()))
      .filter(tournament -> !tournament.participantIds().contains(customerId.get()))
      .switchIfEmpty(Mono.error(new RuntimeException("Ya eres un participante activo")))
      .filter(tournament -> tournament.preRegisteredParticipantIds().contains(customerId.get()))
      .switchIfEmpty(Mono.error(new RuntimeException("Aún no has realizado el pre-registro")))
      .filter(tournament -> !tournament.disqualifiedParticipantIds().contains(customerId.get()))
      .switchIfEmpty(Mono.error(new RuntimeException("No tienes permitido participar en este torneo")))
      .filter(tournament -> !tournament.isFinished())
      .switchIfEmpty(Mono.error(new RuntimeException("El torneo ya terminó")))
      .filter(tournament -> !tournament.isCanceled())
      .switchIfEmpty(Mono.error(new RuntimeException("El torneo fue cancelado")))
      .flatMap(tournament ->
        patchTournamentUseCase.patchRegisterParticipant(customerId.get())
      )
      .doOnNext(tournament ->
        patchRegistrationUseCase.patch(Registration.builder().status(com.fc2o.model.registration.Status.APPROVED).build())
      )
      .doOnNext(ticket -> patchTicketUseCase.patch(Ticket.builder().status(Status.USED).build()));
  }
}

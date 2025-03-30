package com.fc2o.usecase.tournament.business;

import com.fc2o.model.tournament.Status;
import com.fc2o.model.tournament.Tournament;
import com.fc2o.service.ValidateTournamentPermissionsService;
import com.fc2o.usecase.tournament.TournamentUseCases;
import com.fc2o.usecase.tournament.crud.PatchTournamentUseCase;
import com.fc2o.usecase.tournament.crud.RetrieveTournamentUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.LocalDate;


@RequiredArgsConstructor
public class StartTournamentUseCase {

  private final RetrieveTournamentUseCase retrieveTournamentUseCase;
  private final PatchTournamentUseCase patchTournamentUseCase;
  private final ValidateTournamentPermissionsService permissionsService;

  /*
  * Validar que el torneo esté en estado NOT_STARTED
  * Validar que la cantidad de participantes inscritos sea superior a la cantidad mínima para el torneo
  * Validar que la fecha de inicio sea igual a la fecha actual
  * Cambiar el estado a IN_PROGRESS
   */

  public Mono<Tournament> start(String tournamentId, String userId) {
    return retrieveTournamentUseCase.retrieveById(tournamentId)
      .doOnNext(tournament -> permissionsService.validate(tournament.id(), userId, TournamentUseCases.START))
      .filter(tournament -> !tournament.isFinished())
      .switchIfEmpty(Mono.error(new RuntimeException("El torneo ya finalizó")))
      .filter(tournament -> !tournament.isCanceled())
      .switchIfEmpty(Mono.error(new RuntimeException("El torneo fue cancelado")))
      .filter(tournament -> !tournament.isInProgress())
      .switchIfEmpty(Mono.error(new RuntimeException("El torneo ya empezó")))
      .filter(tournament ->
        tournament.placeMinimum().compareTo(Integer.valueOf(tournament.participantIds().size()).shortValue()) <= 0
      )
      .switchIfEmpty(Mono.error(new RuntimeException("Aún no se alcanza la cuota mínima de participantes")))
      .filter(tournament -> tournament.dateStart().isAfter(LocalDate.now()))
      .switchIfEmpty(Mono.error(new RuntimeException("Aún no llega la fecha de inicio del torneo")))
      .map(tournament -> tournament.toBuilder().status(Status.IN_PROGRESS).build())
      .flatMap(patchTournamentUseCase::patch);
  }


  public Mono<Tournament> forceStart(String tournamentId, String userId) {
    return retrieveTournamentUseCase.retrieveById(tournamentId)
      .doOnNext(t -> permissionsService.validate(t.id(), userId, TournamentUseCases.FORCE_START))
      .filter(tournament -> !tournament.isFinished())
      .switchIfEmpty(Mono.error(new RuntimeException("El torneo ya finalizó")))
      .filter(tournament -> !tournament.isCanceled())
      .switchIfEmpty(Mono.error(new RuntimeException("El torneo fue cancelado")))
      .filter(tournament -> !tournament.isInProgress())
      .switchIfEmpty(Mono.error(new RuntimeException("El torneo ya empezó")))
      .map(tournament -> tournament.toBuilder().status(Status.IN_PROGRESS).dateStart(LocalDate.now()).build())
      .flatMap(patchTournamentUseCase::patch);
  }
}

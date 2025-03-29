package com.fc2o.usecase.match.business;

import com.fc2o.model.match.Match;
import com.fc2o.model.match.Status;
import com.fc2o.service.ValidateTournamentPermissionsService;
import com.fc2o.usecase.match.crud.PatchMatchUseCase;
import com.fc2o.usecase.match.crud.RetrieveMatchUseCase;
import com.fc2o.usecase.tournament.TournamentUseCases;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class StartMatchUseCase {

  private final RetrieveMatchUseCase retrieveMatchUseCase;
  private final PatchMatchUseCase patchMatchUseCase;
  private final ValidateTournamentPermissionsService permissionsService;

  /*
  * Validar usuario
  * Validar que la fecha de inicio no sea anterior a hoy
  * Validar que el match se encuentre en estado NOT STARTED
  * Actualizar Match en estado STARTED
   */

  public Mono<Match> startMatch(UUID tournamentId, UUID matchId, UUID userId) {
    return retrieveMatchUseCase.retrieve(matchId)
      .doOnNext(match -> permissionsService.validate(match.tournamentId(), userId, TournamentUseCases.START_MATCH))
      .filter(match -> !match.isInProgress())
      .switchIfEmpty(Mono.error(new RuntimeException("La partida ya empezó")))
      .filter(match -> !match.isFinished())
      .switchIfEmpty(Mono.error(new RuntimeException("La partida ya finalizó")))
      .filter(match -> !match.isCanceled())
      .switchIfEmpty(Mono.error(new RuntimeException("La partida fue cancelada")))
      .filter(match -> match.dateStart().isAfter(LocalDate.now()))
      .switchIfEmpty(Mono.error(new RuntimeException("Aún no llega la fecha de inicio de la partida")))
      .map(match -> match.toBuilder().status(Status.IN_PROGRESS).timeStart(LocalDateTime.now()).build())
      .flatMap(patchMatchUseCase::patch);
  }

  public Mono<Match> forceStartMatch(UUID matchId, UUID userId) {
    return retrieveMatchUseCase.retrieve(matchId)
      .doOnNext(match -> permissionsService.validate(match.tournamentId(), userId, TournamentUseCases.FORCE_START_MATCH))
      .filter(Match::isInProgress)
      .switchIfEmpty(Mono.error(new RuntimeException("La partida ya empezó")))
      .filter(Match::isFinished)
      .switchIfEmpty(Mono.error(new RuntimeException("La partida ya finalizó")))
      .filter(Match::isCanceled)
      .switchIfEmpty(Mono.error(new RuntimeException("La partida fue cancelada")))
      .map(match -> match.toBuilder().status(Status.IN_PROGRESS).dateStart(LocalDate.now()).timeStart(LocalDateTime.now()).build())
      .flatMap(patchMatchUseCase::patch);
  }
}

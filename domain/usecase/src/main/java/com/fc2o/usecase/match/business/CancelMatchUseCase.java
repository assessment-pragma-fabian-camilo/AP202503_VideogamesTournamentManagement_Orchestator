package com.fc2o.usecase.match.business;

import com.fc2o.model.match.Match;
import com.fc2o.model.match.Status;
import com.fc2o.service.ValidateTournamentPermissionsService;
import com.fc2o.usecase.match.crud.PatchMatchUseCase;
import com.fc2o.usecase.match.crud.RetrieveMatchUseCase;
import com.fc2o.usecase.tournament.TournamentUseCases;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class CancelMatchUseCase {
  private final RetrieveMatchUseCase retrieveMatchUseCase;
  private final PatchMatchUseCase patchMatchUseCase;
  private final ValidateTournamentPermissionsService permissionsService;

  /*
  * Validar usuario
  * Validar que la partida no haya sido cancelada previamente o que ya haya terminado
  * Actualizar estado a CANCELED
   */

  public Mono<Match> cancelMatch(UUID matchId, UUID userId) {
    return retrieveMatchUseCase.retrieve(matchId)
      .doOnNext(match -> permissionsService.validate(match.tournamentId(), userId, TournamentUseCases.CANCEL_MATCH))
      .filter(match -> !match.isFinished())
      .switchIfEmpty(Mono.error(new RuntimeException("La partida ya terminó")))
      .filter(match -> !match.isCanceled())
      .switchIfEmpty(Mono.error(new RuntimeException("La partida ya fue cancelada")))
      .map(match -> match.toBuilder().status(Status.CANCELED).build())
      .flatMap(patchMatchUseCase::patch);
  }

  public Flux<Match> cancelAllMatchesByParticipantIdAndTournamentId(UUID participantId, UUID tournamentId) {
    return retrieveMatchUseCase.retrieveAllByParticipantIdAndTournamentId(participantId, tournamentId)
      .filter(match -> !match.isFinished())
      .filter(match -> !match.isCanceled())
      .map(match -> match.toBuilder().status(Status.CANCELED).build())
      .flatMap(patchMatchUseCase::patch);
  }

  public Flux<Match> cancelAllMatchesByTournamentId(UUID tournamentId) {
    return retrieveMatchUseCase.retrieveAllByTournamentId(tournamentId)
      .filter(match -> !match.isFinished())
      .filter(match -> !match.isCanceled())
      .map(match -> match.toBuilder().status(Status.CANCELED).build())
      .flatMap(patchMatchUseCase::patch);
  }
}

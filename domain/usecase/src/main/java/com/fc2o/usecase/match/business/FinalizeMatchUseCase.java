package com.fc2o.usecase.match.business;

import com.fc2o.model.match.Match;
import com.fc2o.model.match.Status;
import com.fc2o.service.ValidateTournamentPermissionsService;
import com.fc2o.usecase.match.crud.PatchMatchUseCase;
import com.fc2o.usecase.match.crud.RetrieveMatchUseCase;
import com.fc2o.usecase.tournament.TournamentUseCases;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class FinalizeMatchUseCase {
  private final RetrieveMatchUseCase retrieveMatchUseCase;
  private final PatchMatchUseCase patchMatchUseCase;
  private final ValidateTournamentPermissionsService permissionsService;

  /*
  * Validar usuario
  * Validar que el estado esté en IN_PROGRESS
  * Validar que el winner sea un participante
   */

  public Mono<Match> finalizeMatch(UUID matchId, UUID userId, UUID winnerId) {
    return retrieveMatchUseCase.retrieve(matchId)
      .doOnNext(match -> permissionsService.validate(match.tournamentId(), userId, TournamentUseCases.FINALIZE_MATCH))
      .filter(Match::isFinished)
      .switchIfEmpty(Mono.error(new RuntimeException("La partida ya finalizó")))
      .filter(Match::isCanceled)
      .switchIfEmpty(Mono.error(new RuntimeException("La partida fue cancelada")))
      .filter(Match::isNotStarted)
      .switchIfEmpty(Mono.error(new RuntimeException("La partida aún no empieza")))
      .filter(match -> match.participantIds().contains(winnerId))
      .switchIfEmpty(Mono.error(new RuntimeException("El ganador no está registrado en esta partida")))
      .map(match -> match.toBuilder().status(Status.FINISHED).timeEnd(LocalDateTime.now()).winnerId(winnerId).build())
      .flatMap(patchMatchUseCase::patch);
  }
}

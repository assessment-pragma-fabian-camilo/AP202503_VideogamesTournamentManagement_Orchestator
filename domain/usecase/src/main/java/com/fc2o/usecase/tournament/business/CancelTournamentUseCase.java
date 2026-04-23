package com.fc2o.usecase.tournament.business;

import com.fc2o.model.tournament.Status;
import com.fc2o.model.tournament.Tournament;
import com.fc2o.usecase.match.business.CancelMatchUseCase;
import com.fc2o.usecase.tournament.TournamentUseCases;
import com.fc2o.service.ValidateTournamentPermissionsService;
import com.fc2o.usecase.tournament.crud.PatchTournamentUseCase;
import com.fc2o.usecase.tournament.crud.RetrieveTournamentUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CancelTournamentUseCase {

  private final RetrieveTournamentUseCase retrieveTournamentUseCase;
  private final PatchTournamentUseCase patchTournamentUseCase;
  private final ValidateTournamentPermissionsService permissionsService;
  private final CancelMatchUseCase cancelMatchUseCase;

  /*
  * Valdiar que el torneo no esté cancelado
  * Validar que el id de usuario de quien hace la petición sea el promotor de deste torneo o sea admin
  * Actualizar el estado a CANCELED
   */

  public Mono<Tournament> cancel(Tournament tournament, String userId) {
    return retrieveTournamentUseCase.retrieveById(tournament.id())
      .doOnNext(t -> permissionsService.validate(t.id(), userId, TournamentUseCases.CANCEL))
      .filter(t -> !t.isFinished())
      .switchIfEmpty(Mono.error(new RuntimeException("El torneo ya terminó")))
      .filter(t -> !t.isCanceled())
      .switchIfEmpty(Mono.error(new RuntimeException("El torneo ya fue cancelado")))
      .map(t -> tournament.toBuilder().status(Status.CANCELED).build())
      .flatMap(patchTournamentUseCase::patch)
      .doOnNext(t -> cancelMatchUseCase.cancelAllMatchesByTournamentId(t.id()));
  }

}

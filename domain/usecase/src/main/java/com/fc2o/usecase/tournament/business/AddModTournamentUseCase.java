package com.fc2o.usecase.tournament.business;

import com.fc2o.model.tournament.Tournament;
import com.fc2o.usecase.tournament.TournamentUseCases;
import com.fc2o.service.ValidateTournamentPermissionsService;
import com.fc2o.usecase.tournament.crud.PatchTournamentUseCase;
import com.fc2o.usecase.tournament.crud.RetrieveTournamentUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class AddModTournamentUseCase {

  private final PatchTournamentUseCase patchTournamentUseCase;
  private final RetrieveTournamentUseCase retrieveTournamentUseCase;
  private final ValidateTournamentPermissionsService permissionsService;
  private final Integer maxModerators;

  public Mono<Tournament> addMod(Tournament tournament, String userId) {
    return retrieveTournamentUseCase.retrieveById(tournament.id())
      .filter(t -> !t.isCanceled())
      .switchIfEmpty(Mono.error(new RuntimeException("El torneo fue cancelado")))
      .filter(t -> !t.isFinished())
      .switchIfEmpty(Mono.error(new RuntimeException("El torneo ya finalizó")))
      .doOnNext(t -> permissionsService.validate(t.id(), userId, TournamentUseCases.ADD_MOD).subscribe())
      .filter(t -> t.moderatorIds().size() + tournament.moderatorIds().size() <= maxModerators)
      .switchIfEmpty(
        Mono.error(new RuntimeException(String.format("La cantidad de moderadores no puede ser mayor a %d", maxModerators)))
      )
      .map(t -> t.toBuilder().moderatorIds(tournament.moderatorIds()).build())
      .flatMap(patchTournamentUseCase::patch);
  }

}

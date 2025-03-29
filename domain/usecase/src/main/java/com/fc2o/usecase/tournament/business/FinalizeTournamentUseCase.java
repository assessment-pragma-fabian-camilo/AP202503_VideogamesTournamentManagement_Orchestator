package com.fc2o.usecase.tournament.business;

import com.fc2o.model.reward.Reward;
import com.fc2o.model.tournament.Status;
import com.fc2o.model.tournament.Tournament;
import com.fc2o.usecase.match.business.CancelMatchUseCase;
import com.fc2o.usecase.match.crud.RetrieveMatchUseCase;
import com.fc2o.usecase.reward.business.RegisterRewardUseCase;
import com.fc2o.usecase.tournament.TournamentUseCases;
import com.fc2o.service.ValidateTournamentPermissionsService;
import com.fc2o.usecase.tournament.crud.PatchTournamentUseCase;
import com.fc2o.usecase.tournament.crud.RetrieveTournamentUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
public class FinalizeTournamentUseCase {

  private final RetrieveTournamentUseCase retrieveTournamentUseCase;
  private final PatchTournamentUseCase patchTournamentUseCase;
  private final ValidateTournamentPermissionsService permissionsService;
  private final RetrieveMatchUseCase retrieveMatchUseCase;
  private final CancelMatchUseCase cancelMatchUseCase;
  private final RegisterRewardUseCase registerRewardUseCase;

  /*
  * Validar que el torneo esté en estado IN_PROGRESS
  * Validar que se haya superado la fecha de finalización
  * Validar que el id de usuario de quien hace la petición sea el promotor de deste torneo o sea admin
  * Cambiar el estado a FINISHED
   */

  public Mono<Tuple2<Tournament, Reward>> finalize(UUID tournamentId, Reward reward, UUID userId) {
    return retrieveTournamentUseCase.retrieveById(tournamentId)
      .doOnNext(tournament -> permissionsService.validate(tournament.id(), userId, TournamentUseCases.FINALIZE))
      .filter(tournament -> !tournament.isNotStarted())
      .switchIfEmpty(Mono.error(new RuntimeException("El torneo aún no empieza")))
      .filter(tournament -> !tournament.isCanceled())
      .switchIfEmpty(Mono.error(new RuntimeException("El torneo fue cancelado")))
      .filter(tournament -> !tournament.isFinished())
      .switchIfEmpty(Mono.error(new RuntimeException("El torneo ya finalizó")))
      .filter(tournament -> tournament.dateEnd().isAfter(LocalDate.now()))
      .switchIfEmpty(Mono.error(new RuntimeException("Aún no se llega a la fecha de finalización del torneo")))
      .map(tournament -> tournament.toBuilder().status(Status.FINISHED).build())
      .doOnNext(tournament ->
        retrieveMatchUseCase.retrieveAllByTournamentId(tournamentId)
          .filter(match -> match.isNotStarted() || match.isInProgress())
          .switchIfEmpty(Mono.error(new RuntimeException("Aún hay partidas sin jugar o en progreso")))
      )
      .flatMap(patchTournamentUseCase::patch)
      .zipWhen(tournament -> registerRewardUseCase.registerReward(reward, tournament));
  }

  public Mono<Tournament> forceFinalize(UUID tournamentId, Reward reward, UUID userId) {
    return retrieveTournamentUseCase.retrieveById(tournamentId)
      .doOnNext(t -> permissionsService.validate(t.id(), userId, TournamentUseCases.FORCE_FINALIZE))
      .filter(tournament -> !tournament.isNotStarted())
      .switchIfEmpty(Mono.error(new RuntimeException("El torneo aún no empieza")))
      .filter(tournament -> !tournament.isCanceled())
      .switchIfEmpty(Mono.error(new RuntimeException("El torneo fue cancelado")))
      .filter(tournament -> !tournament.isFinished())
      .switchIfEmpty(Mono.error(new RuntimeException("El torneo ya finalizó")))
      .map(tournament -> tournament.toBuilder().status(Status.FINISHED).dateEnd(LocalDate.now()).build())
      .doOnNext(tournament -> cancelMatchUseCase.cancelAllMatchesByTournamentId(tournamentId))
      .doOnNext(tournament -> registerRewardUseCase.registerReward(reward, tournament))
      .flatMap(patchTournamentUseCase::patch);
  }

}

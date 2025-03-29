package com.fc2o.usecase.tournament.business;

import com.fc2o.model.tournament.Status;
import com.fc2o.model.tournament.Tournament;
import com.fc2o.service.ValidateTournamentPermissionsService;
import com.fc2o.usecase.tournament.TournamentUseCases;
import com.fc2o.usecase.tournament.crud.PatchTournamentUseCase;
import com.fc2o.usecase.tournament.crud.RetrieveTournamentUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class RescheduleTournamentUseCase {

  private final RetrieveTournamentUseCase retrieveTournamentUseCase;
  private final PatchTournamentUseCase patchTournamentUseCase;
  private final ValidateTournamentPermissionsService permissionsService;

  /*
  * Validar que el estado no sea CANCELED, IN PROGRESS o FINISHED
   */

  public Mono<Tournament> reschedule(Tournament tournament, UUID userId) {
    return retrieveTournamentUseCase.retrieveById(tournament.id())
      .doOnNext(t -> permissionsService.validate(t.id(), userId, TournamentUseCases.RESCHEDULE))
      .zipWhen(this::rescheduleStartDate)
      .zipWhen(objects -> rescheduleEndDate(objects.getT1()))
      .map(objects -> objects.getT1().getT1())
      .flatMap(t -> patchTournamentUseCase.patch(tournament));
  }

  public Mono<Tournament> forceReschedule(Tournament tournament, UUID userId) {
    return retrieveTournamentUseCase.retrieveById(tournament.id())
      .doOnNext(t -> permissionsService.validate(t.id(), userId, TournamentUseCases.FORCE_RESCHEDULE))
      .filter(t -> !t.isFinished())
      .switchIfEmpty(Mono.error(new RuntimeException("El torneo ya finalizó")))
      .map(t -> t.toBuilder().status(Status.NOT_STARTED).build())
      .flatMap(t -> patchTournamentUseCase.patch(tournament));
  }

  public Mono<Tournament> rescheduleStartDate(Tournament tournament) {
    return Mono.just(tournament)
      .filter(t -> t.dateStart() != null)
      .filter(t -> !t.isInProgress())
      .switchIfEmpty(Mono.error(new RuntimeException("El torneo ya inició")));
  }

  public Mono<Tournament> rescheduleEndDate(Tournament tournament) {
    return Mono.just(tournament)
      .filter(t -> t.dateEnd() != null)
      .filter(t -> !t.isCanceled())
      .switchIfEmpty(Mono.error(new RuntimeException("El torneo fue cancelado")))
      .filter(t -> !t.isFinished())
      .switchIfEmpty(Mono.error(new RuntimeException("El torneo ya finalizó")));
  }
}

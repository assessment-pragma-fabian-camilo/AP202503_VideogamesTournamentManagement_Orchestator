package com.fc2o.usecase.match.business;

import com.fc2o.model.match.Match;
import com.fc2o.model.match.Status;
import com.fc2o.service.ValidateTournamentPermissionsService;
import com.fc2o.usecase.match.crud.CreateMatchUseCase;
import com.fc2o.usecase.tournament.TournamentUseCases;
import com.fc2o.usecase.tournament.crud.RetrieveTournamentUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
public class RegisterMatchUseCase {
  private final RetrieveTournamentUseCase retrieveTournamentUseCase;
  private final CreateMatchUseCase createMatchUseCase;
  private final ValidateTournamentPermissionsService permissionsService;

  /*
  * Validar que todos los participantes están en el torneo
  * Validar permisos de quien crea el match sobre el torneo
  * Crear el match en estado NOT_STARTED
   */

  public Mono<Match> registerMatch(String tournamentId, Match match, String userId) {
    AtomicReference<StringBuffer> StringsNotRegistered = new AtomicReference<>();
    return retrieveTournamentUseCase.retrieveById(tournamentId)
      .doOnNext(tournament -> permissionsService.validate(tournamentId, userId, TournamentUseCases.REGISTER_MATCH))
      .flatMapMany(tournament ->
        Flux.fromIterable(match.participantIds())
          .filter(String -> !tournament.participantIds().contains(String))
          .doOnNext(String -> StringsNotRegistered.get().append(", ").append(String.toString()))
      )
      .singleOrEmpty()
      .switchIfEmpty(
        Mono.error(
          new RuntimeException(
            String.format(
              "Los participantes (%s) no están registrados para este torneo",
              StringsNotRegistered.get().substring(2)
            )
          )
        )
      )
      .map(String -> match.toBuilder().status(Status.NOT_STARTED).build())
      .flatMap(createMatchUseCase::create);
  }
}

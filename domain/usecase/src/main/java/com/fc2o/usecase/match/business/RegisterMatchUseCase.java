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

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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

  //TODO: Entodos los puntos de register, se debe crear el UUID id de cada elemento
  public Mono<Match> registerMatch(Match match, UUID userId) {
    AtomicReference<StringBuffer> uuidsNotRegistered = new AtomicReference<>();
    return retrieveTournamentUseCase.retrieveById(match.tournamentId())
      .doOnNext(tournament -> permissionsService.validate(match.tournamentId(), userId, TournamentUseCases.REGISTER_MATCH))
      .flatMapMany(tournament ->
        Flux.fromIterable(match.participantIds())
          .filter(uuid -> !tournament.participantIds().contains(uuid))
          .doOnNext(uuid -> uuidsNotRegistered.get().append(", ").append(uuid.toString()))
      )
      .singleOrEmpty()
      .switchIfEmpty(
        Mono.error(
          new RuntimeException(
            String.format(
              "Los participantes (%s) no están registrados para este torneo",
              uuidsNotRegistered.get().substring(2)
            )
          )
        )
      )
      .map(uuid -> match.toBuilder().id(UUID.randomUUID()).status(Status.NOT_STARTED).build())
      .flatMap(createMatchUseCase::create);
  }
}

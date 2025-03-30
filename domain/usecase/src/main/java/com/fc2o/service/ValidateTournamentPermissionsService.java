package com.fc2o.service;

import com.fc2o.model.tournament.Tournament;
import com.fc2o.model.user.Role;

import com.fc2o.usecase.UseCase;
import com.fc2o.usecase.tournament.TournamentUseCases;
import com.fc2o.usecase.tournament.crud.RetrieveTournamentUseCase;
import com.fc2o.usecase.user.crud.RetrieveUserUseCase;
import lombok.Getter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Getter
public class ValidateTournamentPermissionsService extends ValidatePermissionsService {

  private final RetrieveTournamentUseCase retrieveTournamentUseCase;

  public ValidateTournamentPermissionsService(
    Map<Class<?>, Map<? extends UseCase, List<Role>>> roles,
    RetrieveUserUseCase retrieveUserUseCase,
    RetrieveTournamentUseCase retrieveTournamentUseCase
  ) {
    super(roles, retrieveUserUseCase);
    this.retrieveTournamentUseCase = retrieveTournamentUseCase;
  }

  public void validate(String tournamentId, String userId, TournamentUseCases tournamentUseCases) {
    super.validate(userId, super.roles.get(Tournament.class).get(tournamentUseCases));

    Mono<Tournament> tournamentMono = retrieveTournamentUseCase.retrieveById(tournamentId);

    super.validatorRoles
      .get(Role.PROMOTER)
      .put(Tournament.class, tournament -> ((Tournament) tournament).promoterId().equals(userId));
    super.validatorRoles
      .get(Role.MODERATOR)
      .put(Tournament.class, tournament -> ((Tournament) tournament).moderatorIds().contains(userId));

    Flux.fromIterable(super.roles.get(Tournament.class).get(tournamentUseCases))
      .flatMap(role -> tournamentMono.map(validatorRoles.get(role).get(Tournament.class)))
      .filter(hasRole -> hasRole)
      .singleOrEmpty()
      .switchIfEmpty(Mono.error(new RuntimeException("No tienes permisos suficientes para realizar esta operación")))
      .then();
  }

}

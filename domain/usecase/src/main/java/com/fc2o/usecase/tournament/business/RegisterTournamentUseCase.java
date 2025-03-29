package com.fc2o.usecase.tournament.business;

import com.fc2o.model.tournament.Tournament;
import com.fc2o.model.user.User;
import com.fc2o.service.ValidatePermissionsService;
import com.fc2o.usecase.tournament.crud.CreateTournamentUseCase;
import com.fc2o.usecase.tournament.crud.RetrieveTournamentUseCase;
import com.fc2o.usecase.user.UserUseCases;
import com.fc2o.usecase.user.crud.RetrieveUserUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
public class RegisterTournamentUseCase {
  /*
  Validar usuario: PROMOTER
  Si el torneo es gratis:
    Consultar todos los torneos que tiene el promotor. Si es mayor a 2, lanzar excepción
    La cantidad de participantes está definida por el sistema
  Si el torneo es de pago
   */

  private final RetrieveUserUseCase retrieveUserUseCase;
  private final RetrieveTournamentUseCase retrieveTournamentUseCase;
  private final CreateTournamentUseCase createTournamentUseCase;
  private final ValidatePermissionsService permissionsService;
  private final Short maxFreeTournaments;

  public Mono<Tournament> registerTournament(Tournament tournament) {
    AtomicReference<User> userAtomicReference = new AtomicReference<>();
    return Mono.just(tournament)
      .doOnNext(t -> permissionsService.validate(tournament.promoterId(), UserUseCases.REGISTER_TOURNAMENT))
      .filter(Tournament::isPaid)
      .switchIfEmpty(createTournamentUseCase.create(tournament))
      .flatMap(
        t ->
          retrieveUserUseCase.retrieveById(t.promoterId())
            .doOnNext(userAtomicReference::set)
            .flatMapMany(user -> Flux.from(retrieveTournamentUseCase.findAllByPromoterId(user.id())))
            .count()
            .filter(totalFreeTournaments -> totalFreeTournaments.shortValue() < this.maxFreeTournaments)
            .switchIfEmpty(
              Mono.error(
                new RuntimeException(String.format("Superaste la cantidad máxima (%d) de torneos gratis", maxFreeTournaments))
              )
            )
            .map(totalFreeTournaments -> tournament)
            .flatMap(createTournamentUseCase::create)
      );
  }
}

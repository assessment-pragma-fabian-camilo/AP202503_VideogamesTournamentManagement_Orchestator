package com.fc2o.usecase.tournament.business;

import com.fc2o.model.tournament.Commission;
import com.fc2o.model.tournament.Price;
import com.fc2o.model.tournament.Status;
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

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
public class RegisterTournamentUseCase {
  /*
  Validar usuario: PROMOTER
  Si el torneo es gratis:
    Consultar todos los torneos que tiene el promotor. Si es mayor a 2, lanzar excepción
    La cantidad de participantes está definida por el sistema
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
      .filter(Tournament::isFree)
      .flatMap(
        t ->
          retrieveUserUseCase.retrieveById(t.promoterId())
            .doOnNext(userAtomicReference::set)
            .flatMapMany(user -> Flux.from(retrieveTournamentUseCase.findAllByPromoterId(user.id())))
            .filter(Tournament::isFree)
            .count()
            .filter(totalFreeTournaments -> totalFreeTournaments.shortValue() < this.maxFreeTournaments)
            .switchIfEmpty(
              Mono.error(
                new RuntimeException(String.format("Superaste la cantidad máxima (%d) de torneos gratis", maxFreeTournaments))
              )
            )
            .map(totalFreeTournaments -> tournament)
            .map(t2 -> t2.toBuilder().status(Status.NOT_STARTED).build())
            .map(t2 -> t2.toBuilder().placeRemaining(t2.placeLimit()).build())
            .map(t2 -> t2.toBuilder()
              .commission(
                Commission.builder().visualizationPercentage(0.00F).participationPercentage(0.00F).donationPercentage(0.00F).build()
              )
              .build()
            )
            .map(t2 -> t2.toBuilder()
              .price(
                Price.builder().participation(BigDecimal.valueOf(0.00F)).visualization(BigDecimal.valueOf(0.00F)).build()
              )
              .build()
            )
            .flatMap(createTournamentUseCase::create)
      )
      .switchIfEmpty(createTournamentUseCase.create(tournament.toBuilder().status(Status.NOT_STARTED).build()));
  }
}

package com.fc2o.usecase.donation.business;

import com.fc2o.model.donation.Donation;
import com.fc2o.usecase.donation.crud.CreateDonationUseCase;
import com.fc2o.usecase.tournament.crud.RetrieveTournamentUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class RegisterDonationUseCase {
  private final CreateDonationUseCase createDonationUseCase;
  private final RetrieveTournamentUseCase retrieveTournamentUseCase;

  public Mono<Donation> registerDonation(Donation donation) {
    return retrieveTournamentUseCase.retrieveById(donation.tournamentId())
      .filter(tournament -> !tournament.isCanceled())
      .switchIfEmpty(Mono.error(new RuntimeException("El torneo fue cancelado")))
      .filter(tournament -> !tournament.isFinished())
      .switchIfEmpty(Mono.error(new RuntimeException("El torneo ya finalizó")))
      .map(tournament -> donation.toBuilder().build())
      .flatMap(createDonationUseCase::create);
  }
}

package com.fc2o.usecase.tournament.crud;

import com.fc2o.model.tournament.Tournament;
import com.fc2o.model.tournament.gateways.TournamentRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateTournamentUseCase {
  private final TournamentRepository tournamentRepository;

  public Mono<Tournament> create(Tournament tournament) {
    return tournamentRepository.save(tournament);
  }
}

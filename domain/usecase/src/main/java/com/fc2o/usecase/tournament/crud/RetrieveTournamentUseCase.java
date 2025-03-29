package com.fc2o.usecase.tournament.crud;

import com.fc2o.model.tournament.Tournament;
import com.fc2o.model.tournament.gateways.TournamentRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class RetrieveTournamentUseCase {
  private final TournamentRepository tournamentRepository;

  public Mono<Tournament> retrieveById(UUID id) {
    return tournamentRepository.findById(id);
  }

  public Flux<Tournament> findAllByPromoterId(UUID promoterId) {
    return tournamentRepository.findAllByPromoterId(promoterId);
  }
}

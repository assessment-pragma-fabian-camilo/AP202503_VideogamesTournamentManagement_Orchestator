package com.fc2o.usecase.tournament.crud;

import com.fc2o.model.tournament.Tournament;
import com.fc2o.model.tournament.gateways.TournamentRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@RequiredArgsConstructor
public class RetrieveTournamentUseCase {
  private final TournamentRepository tournamentRepository;

  public Mono<Tournament> retrieveById(String id) {
    return tournamentRepository.findById(id);
  }

  public Flux<Tournament> findAllByPromoterId(String promoterId) {
    return tournamentRepository.findAllByPromoterId(promoterId);
  }
}

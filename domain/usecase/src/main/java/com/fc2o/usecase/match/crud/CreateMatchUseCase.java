package com.fc2o.usecase.match.crud;

import com.fc2o.model.match.Match;
import com.fc2o.model.match.gateways.MatchRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateMatchUseCase {
  private final MatchRepository matchRepository;

  public Mono<Match> create(Match match) {
    return matchRepository.save(match);
  }
}

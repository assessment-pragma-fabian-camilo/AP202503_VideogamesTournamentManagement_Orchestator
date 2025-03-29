package com.fc2o.usecase.match.crud;

import com.fc2o.model.match.Match;
import com.fc2o.model.match.gateways.MatchRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class PatchMatchUseCase {
  private final MatchRepository matchRepository;

  public Mono<Match> patch(Match match) {
    return matchRepository.patch(match);
  }
}

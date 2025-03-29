package com.fc2o.api.impl;

import com.fc2o.model.match.gateways.MatchRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class Match implements MatchRepository {
  @Override
  public Mono<com.fc2o.model.match.Match> findById(UUID id) {
    return null;
  }

  @Override
  public Flux<com.fc2o.model.match.Match> findAll() {
    return null;
  }

  @Override
  public Flux<com.fc2o.model.match.Match> findAllByTournamentId(UUID tournamentId) {
    return null;
  }

  @Override
  public Flux<com.fc2o.model.match.Match> findAllByParticipantIdAndTournamentId(UUID participantId, UUID tournamentId) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.match.Match> save(com.fc2o.model.match.Match match) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.match.Match> update(com.fc2o.model.match.Match match) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.match.Match> patch(com.fc2o.model.match.Match match) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.match.Match> deleteById(UUID id) {
    return null;
  }
}

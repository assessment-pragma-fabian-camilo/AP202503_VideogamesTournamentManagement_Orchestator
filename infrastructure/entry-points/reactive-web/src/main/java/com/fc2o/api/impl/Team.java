package com.fc2o.api.impl;

import com.fc2o.model.team.gateways.TeamRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class Team implements TeamRepository {
  @Override
  public Mono<com.fc2o.model.team.Team> findById(UUID id) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.team.Team> findByCustomerId(UUID id) {
    return null;
  }

  @Override
  public Flux<com.fc2o.model.team.Team> findAll() {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.team.Team> save(com.fc2o.model.team.Team team) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.team.Team> update(com.fc2o.model.team.Team team) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.team.Team> patch(com.fc2o.model.team.Team team) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.team.Team> deleteById(UUID id) {
    return null;
  }
}

package com.fc2o.model.team.gateways;

import com.fc2o.model.team.Team;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TeamRepository {
  Mono<Team> findById(UUID id);
  Flux<Team> findAll();
  Mono<Team> save(Team team);
  Mono<Team> update(Team team);
  Mono<Team> patch(Team team);
  Mono<Team> deleteById(UUID id);
}

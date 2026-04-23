package com.fc2o.api.impl;

import com.fc2o.model.game.gateways.GameRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class Game implements GameRepository {
  @Override
  public Mono<com.fc2o.model.game.Game> findById(UUID id) {
    return null;
  }

  @Override
  public Flux<com.fc2o.model.game.Game> findAll() {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.game.Game> save(com.fc2o.model.game.Game game) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.game.Game> update(com.fc2o.model.game.Game game) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.game.Game> patch(com.fc2o.model.game.Game game) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.game.Game> deleteById(UUID id) {
    return null;
  }
}

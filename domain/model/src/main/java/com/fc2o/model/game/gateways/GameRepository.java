package com.fc2o.model.game.gateways;

import com.fc2o.model.game.Game;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface GameRepository {
  Mono<Game> findById(String id);
  Flux<Game> findAll();
  Mono<Game> save(Game game);
  Mono<Game> update(Game game);
  Mono<Game> patch(Game game);
  Mono<Game> deleteById(String id);
}

package com.fc2o.airtable.game;

import com.fc2o.airtable.game.mapper.GameMapper;
import com.fc2o.model.game.Game;
import com.fc2o.model.game.gateways.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GameHandler implements GameRepository {

  private final GameWebClient webClient;
  private final GameMapper mapper;

  @Override
  public Mono<Game> findById(String id) {
    return webClient.retrieveById(id)
      .map(mapper::toGame);
  }

  @Override
  public Flux<Game> findAll() {
    return webClient.retrieveAll()
      .flatMapMany(dto -> Flux.fromIterable(dto.records()))
      .map(mapper::toGame);
  }

  @Override
  public Mono<Game> save(Game game) {
    return webClient
      .create(mapper.toWrapperDto(game))
      .map(dto -> mapper.toGame(dto.records().getFirst()));
  }

  @Override
  public Mono<Game> update(Game game) {
    return null;
  }

  @Override
  public Mono<Game> patch(Game game) {
    return webClient
      .patch(mapper.toWrapperDto(game))
      .map(dto -> mapper.toGame(dto.records().getFirst()));
  }

  @Override
  public Mono<Game> deleteById(String id) {
    return null;
  }
}

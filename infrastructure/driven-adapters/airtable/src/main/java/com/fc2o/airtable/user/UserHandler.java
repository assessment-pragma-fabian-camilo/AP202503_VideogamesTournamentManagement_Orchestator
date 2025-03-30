package com.fc2o.airtable.user;

import com.fc2o.airtable.user.mapper.UserMapper;
import com.fc2o.model.user.User;
import com.fc2o.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserHandler implements UserRepository {

  private final UserWebClient webClient;
  private final UserMapper mapper;

  @Override
  public Mono<User> findById(String id) {
    return webClient.retrieveById(id)
      .map(mapper::toUser);
  }

  @Override
  public Flux<User> findAll() {
    return webClient.retrieveAll()
      .flatMapMany(dto -> Flux.fromIterable(dto.records()))
      .map(mapper::toUser);
  }

  @Override
  public Flux<User> findAllById(Set<String> ids) {
    return webClient.retrieveAll()
      .flatMapMany(dto -> Flux.fromIterable(dto.records()))
      .filter(dto -> ids.contains(dto.id()))
      .map(mapper::toUser);
  }

  @Override
  public Mono<User> save(User user) {
    return null;
  }

  @Override
  public Mono<User> update(User user) {
    return null;
  }

  @Override
  public Mono<User> patch(User user) {
    return webClient
      .patch(mapper.toWrapperDto(user))
      .map(dto -> mapper.toUser(dto.records().getFirst()));
  }

  @Override
  public Mono<User> deleteById(String id) {
    return null;
  }
}

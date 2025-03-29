package com.fc2o.api.impl;

import com.fc2o.model.user.gateways.UserRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.UUID;

@Component
public class User implements UserRepository {
  @Override
  public Mono<com.fc2o.model.user.User> findById(UUID id) {
    return Mono.just(com.fc2o.model.user.User.builder().id(UUID.randomUUID()).build());
  }

  @Override
  public Flux<com.fc2o.model.user.User> findAll() {
    return null;
  }

  @Override
  public Flux<com.fc2o.model.user.User> findAllById(Set<UUID> ids) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.user.User> save(com.fc2o.model.user.User user) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.user.User> update(com.fc2o.model.user.User user) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.user.User> patch(com.fc2o.model.user.User user) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.user.User> deleteById(UUID id) {
    return null;
  }
}

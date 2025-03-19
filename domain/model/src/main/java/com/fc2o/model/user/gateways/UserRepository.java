package com.fc2o.model.user.gateways;

import com.fc2o.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRepository {
  Mono<User> findById(UUID id);
  Flux<User> findAll();
  Mono<User> save(User user);
  Mono<User> update(User user);
  Mono<User> patch(User user);
  Mono<User> deleteById(UUID id);
}

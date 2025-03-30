package com.fc2o.model.user.gateways;

import com.fc2o.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface UserRepository {
  Mono<User> findById(String id);
  Flux<User> findAll();
  Flux<User> findAllById(Set<String> ids);
  Mono<User> save(User user);
  Mono<User> update(User user);
  Mono<User> patch(User user);
  Mono<User> deleteById(String id);
}

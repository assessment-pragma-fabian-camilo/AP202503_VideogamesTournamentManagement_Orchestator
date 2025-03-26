package com.fc2o.usecase.user.crud;

import com.fc2o.model.user.User;
import com.fc2o.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
public class RetrieveUserUseCase {

  private final UserRepository userRepository;

  public Mono<User> retrieveById(UUID id) {
    return userRepository.findById(id);
  }

  public Flux<User> retrieveAllById(Set<UUID> ids) {
    return userRepository.findAllById(ids);
  }
}

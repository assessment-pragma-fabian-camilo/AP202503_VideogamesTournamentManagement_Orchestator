package com.fc2o.usecase.user.crud;

import com.fc2o.model.user.User;
import com.fc2o.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class PatchUserUseCase {
  private final UserRepository userRepository;

  public Mono<User> patch(User user) {
    return userRepository.patch(user);
  }
}

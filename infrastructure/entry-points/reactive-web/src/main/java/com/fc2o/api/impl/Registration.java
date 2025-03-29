package com.fc2o.api.impl;

import com.fc2o.model.registration.gateways.RegistrationRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class Registration implements RegistrationRepository {
  @Override
  public Mono<com.fc2o.model.registration.Registration> findById(UUID id) {
    return null;
  }

  @Override
  public Flux<com.fc2o.model.registration.Registration> findAll() {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.registration.Registration> save(com.fc2o.model.registration.Registration registration) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.registration.Registration> update(com.fc2o.model.registration.Registration registration) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.registration.Registration> patch(com.fc2o.model.registration.Registration registration) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.registration.Registration> deleteById(UUID id) {
    return null;
  }
}

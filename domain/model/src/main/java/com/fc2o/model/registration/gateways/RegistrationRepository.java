package com.fc2o.model.registration.gateways;

import com.fc2o.model.registration.Registration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface RegistrationRepository {
  Mono<Registration> findById(UUID id);
  Flux<Registration> findAll();
  Mono<Registration> save(Registration registration);
  Mono<Registration> update(Registration registration);
  Mono<Registration> patch(Registration registration);
  Mono<Registration> deleteById(UUID id);
}

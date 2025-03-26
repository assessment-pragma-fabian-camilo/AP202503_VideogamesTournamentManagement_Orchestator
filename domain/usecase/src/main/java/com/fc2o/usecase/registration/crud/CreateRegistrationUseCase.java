package com.fc2o.usecase.registration.crud;

import com.fc2o.model.registration.Registration;
import com.fc2o.model.registration.gateways.RegistrationRepository;
import reactor.core.publisher.Mono;

public class CreateRegistrationUseCase {

  private final RegistrationRepository registrationRepository;

  public CreateRegistrationUseCase(RegistrationRepository registrationRepository) {
    this.registrationRepository = registrationRepository;
  }

  public Mono<Registration> create(Registration registration) {
    return registrationRepository.save(registration);
  }
}

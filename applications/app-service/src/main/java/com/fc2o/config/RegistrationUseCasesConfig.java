package com.fc2o.config;

import com.fc2o.model.registration.gateways.RegistrationRepository;
import com.fc2o.usecase.registration.crud.CreateRegistrationUseCase;
import com.fc2o.usecase.registration.crud.PatchRegistrationUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "com.fc2o.usecase.registration",
  includeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")},
  useDefaultFilters = false
)
public class RegistrationUseCasesConfig {
  @Bean
  public CreateRegistrationUseCase createRegistrationUseCase(RegistrationRepository registrationRepository) {
    return new CreateRegistrationUseCase(registrationRepository);
  }

  @Bean
  public PatchRegistrationUseCase patchRegistrationUseCase(RegistrationRepository registrationRepository) {
    return new PatchRegistrationUseCase(registrationRepository);
  }
}

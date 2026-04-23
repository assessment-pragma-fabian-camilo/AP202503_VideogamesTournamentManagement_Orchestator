package com.fc2o.config;

import com.fc2o.model.user.gateways.UserRepository;
import com.fc2o.service.ValidatePermissionsService;
import com.fc2o.usecase.user.business.BanUserUseCase;
import com.fc2o.usecase.user.business.UnbanUserUseCase;
import com.fc2o.usecase.user.crud.PatchUserUseCase;
import com.fc2o.usecase.user.crud.RetrieveUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "com.fc2o.usecase.user",
  includeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")},
  useDefaultFilters = false
)
public class UserUseCasesConfig {

  @Bean
  public BanUserUseCase banUserUseCase(
    RetrieveUserUseCase retrieveUserUseCase,
    PatchUserUseCase patchUserUseCase,
    ValidatePermissionsService permissionsService
  ) {
    return new BanUserUseCase(retrieveUserUseCase, patchUserUseCase, permissionsService);
  }

  @Bean
  public UnbanUserUseCase unbanUserUseCase(
    RetrieveUserUseCase retrieveUserUseCase,
    PatchUserUseCase patchUserUseCase,
    ValidatePermissionsService permissionsService
  ) {
    return new UnbanUserUseCase(retrieveUserUseCase, patchUserUseCase, permissionsService);
  }

  @Bean PatchUserUseCase patchUserUseCase(UserRepository userRepository) {
    return new PatchUserUseCase(userRepository);
  }

  @Bean RetrieveUserUseCase retrieveUserUseCase(UserRepository userRepository) {
    return new RetrieveUserUseCase(userRepository);
  }
}

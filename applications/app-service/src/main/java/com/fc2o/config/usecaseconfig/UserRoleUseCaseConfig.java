package com.fc2o.config.usecaseconfig;

import com.fc2o.model.userrole.gateways.UserRoleRepository;
import com.fc2o.model.user.gateways.UserRepository;
import com.fc2o.usecase.userrole.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for User Role use cases.
 */
@Configuration
public class UserRoleUseCaseConfig {

    @Bean
    public AssignRoleUseCase assignRoleUseCase(
            UserRoleRepository userRoleRepository,
            UserRepository userRepository) {
        return new AssignRoleUseCase(userRoleRepository, userRepository);
    }

    @Bean
    public GetUserRolesUseCase getUserRolesUseCase(
            UserRoleRepository userRoleRepository) {
        return new GetUserRolesUseCase(userRoleRepository);
    }

    @Bean
    public RemoveRoleUseCase removeRoleUseCase(
            UserRoleRepository userRoleRepository) {
        return new RemoveRoleUseCase(userRoleRepository);
    }
}


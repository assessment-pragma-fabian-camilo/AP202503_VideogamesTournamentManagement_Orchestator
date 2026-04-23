package com.fc2o.usecase.userrole;

import com.fc2o.model.userrole.UserRole;
import com.fc2o.model.userrole.gateways.UserRoleRepository;
import reactor.core.publisher.Flux;

/**
 * Use case for retrieving all roles of a user.
 */
public class GetUserRolesUseCase {
    private final UserRoleRepository userRoleRepository;

    public GetUserRolesUseCase(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public Flux<UserRole> execute(String userId) {
        if (userId == null || userId.isBlank()) {
            return Flux.error(new IllegalArgumentException("User ID is required"));
        }
        return userRoleRepository.findAllByUserId(userId);
    }
}


package com.fc2o.usecase.userrole;

import com.fc2o.model.userrole.gateways.UserRoleRepository;
import reactor.core.publisher.Mono;

/**
 * Use case for removing a role from a user.
 * Business rule: Only ADMIN can remove PROMOTER role.
 */
public class RemoveRoleUseCase {
    private final UserRoleRepository userRoleRepository;

    public RemoveRoleUseCase(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public Mono<Void> execute(String userRoleId) {
        if (userRoleId == null || userRoleId.isBlank()) {
            return Mono.error(new IllegalArgumentException("User Role ID is required"));
        }

        return userRoleRepository.deleteById(userRoleId);
    }
}


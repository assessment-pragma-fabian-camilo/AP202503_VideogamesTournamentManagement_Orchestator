package com.fc2o.usecase.userrole;

import com.fc2o.model.userrole.Role;
import com.fc2o.model.userrole.UserRole;
import com.fc2o.model.userrole.gateways.UserRoleRepository;
import com.fc2o.model.user.gateways.UserRepository;
import reactor.core.publisher.Mono;

/**
 * Use case for assigning a role to a user.
 * Business rules:
 * - Only ADMIN can assign PROMOTER role
 * - User can self-assign PLAYER role
 * - User can self-assign MODERATOR role
 */
public class AssignRoleUseCase {
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;

    public AssignRoleUseCase(
            UserRoleRepository userRoleRepository,
            UserRepository userRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
    }

    public Mono<UserRole> execute(String userId, Role role) {
        if (userId == null || userId.isBlank()) {
            return Mono.error(new IllegalArgumentException("User ID is required"));
        }
        if (role == null) {
            return Mono.error(new IllegalArgumentException("Role is required"));
        }

        // Verify user exists
        return userRepository.isActiveUser(userId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("User not found")))
                .flatMap(user -> {
                    UserRole newRole = UserRole.builder()
                            .userId(userId)
                            .role(role)
                            .build();
                    
                    return userRoleRepository.save(newRole);
                });
    }
}


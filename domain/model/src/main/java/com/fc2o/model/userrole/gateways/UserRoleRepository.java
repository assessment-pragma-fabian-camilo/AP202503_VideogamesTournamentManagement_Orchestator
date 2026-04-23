package com.fc2o.model.userrole.gateways;

import com.fc2o.model.userrole.Role;
import com.fc2o.model.userrole.UserRole;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Gateway interface for UserRole repository operations.
 * Defines all operations needed by user role use cases.
 */
public interface UserRoleRepository {
    /**
     * Find all roles for a specific user.
     */
    Flux<UserRole> findAllByUserId(String userId);

    /**
     * Check if a user has a specific role.
     */
    Mono<Boolean> existsByUserIdAndRole(String userId, Role role);

    /**
     * Create a new user role assignment.
     */
    Mono<UserRole> save(UserRole userRole);

    /**
     * Delete a user role assignment.
     */
    Mono<Void> deleteByUserIdAndRole(String userId, Role role);

    /**
     * Delete user role by ID.
     */
    Mono<Void> deleteById(String id);
}


package com.fc2o.model.team.gateways;

import com.fc2o.model.team.TeamMember;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Gateway interface for TeamMember repository operations.
 * Defines all operations needed by team member use cases.
 */
public interface TeamMemberRepository {
    /**
     * Find all members of a specific team.
     */
    Flux<TeamMember> findAllByTeamId(String teamId);

    /**
     * Find all teams for a specific user.
     */
    Flux<TeamMember> findAllByUserId(String userId);

    /**
     * Check if a user is a member of a specific team.
     */
    Mono<Boolean> existsByTeamIdAndUserId(String teamId, String userId);

    /**
     * Create a new team member assignment.
     */
    Mono<TeamMember> save(TeamMember teamMember);

    /**
     * Delete a team member assignment.
     */
    Mono<Void> deleteByTeamIdAndUserId(String teamId, String userId);

    /**
     * Delete team member by ID.
     */
    Mono<Void> deleteById(String id);
}


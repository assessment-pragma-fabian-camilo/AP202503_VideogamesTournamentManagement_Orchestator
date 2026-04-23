package com.fc2o.model.team.gateways;

import com.fc2o.model.team.Team;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Gateway interface for Team repository operations.
 * Defines all operations needed by team use cases.
 */
public interface TeamRepository {
    /**
     * Find team by ID.
     */
    Mono<Team> findById(String teamId);

    /**
     * Find all teams.
     */
    Flux<Team> findAll();

    /**
     * Find all teams led by a specific user.
     */
    Flux<Team> findByLeaderUserId(String leaderUserId);

    /**
     * Create a new team.
     */
    Mono<Team> save(Team team);

    /**
     * Update an entire team.
     */
    Mono<Team> update(Team team);

    /**
     * Delete team by ID.
     */
    Mono<Void> deleteById(String teamId);
}


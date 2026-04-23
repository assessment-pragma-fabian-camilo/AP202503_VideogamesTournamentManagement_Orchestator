package com.fc2o.model.reward.gateways;

import com.fc2o.model.reward.Reward;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Gateway interface for Reward repository operations.
 * Defines all operations needed by reward use cases.
 */
public interface RewardRepository {
    /**
     * Find reward by ID.
     */
    Mono<Reward> findById(String id);

    /**
     * Find all rewards.
     */
    Flux<Reward> findAll();

    /**
     * Find all rewards for a specific tournament.
     */
    Flux<Reward> findAllByTournamentId(String tournamentId);

    /**
     * Find reward by tournament and position.
     */
    Mono<Reward> findByTournamentIdAndPosition(String tournamentId, Short position);

    /**
     * Create a new reward.
     */
    Mono<Reward> save(Reward reward);

    /**
     * Update an entire reward.
     */
    Mono<Reward> update(Reward reward);

    /**
     * Delete reward by ID.
     */
    Mono<Void> deleteById(String id);
}

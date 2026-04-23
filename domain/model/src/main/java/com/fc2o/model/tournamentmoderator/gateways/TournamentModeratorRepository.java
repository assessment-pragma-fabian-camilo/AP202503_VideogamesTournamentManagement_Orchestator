package com.fc2o.model.tournamentmoderator.gateways;

import com.fc2o.model.tournamentmoderator.TournamentModerator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Gateway interface for TournamentModerator repository operations.
 * Defines all operations needed by tournament moderator use cases.
 */
public interface TournamentModeratorRepository {
    /**
     * Find all moderators for a specific tournament.
     */
    Flux<TournamentModerator> findAllByTournamentId(String tournamentId);

    /**
     * Check if a user is a moderator of a specific tournament.
     */
    Mono<Boolean> existsByTournamentIdAndUserId(String tournamentId, String userId);

    /**
     * Create a new tournament moderator assignment.
     */
    Mono<TournamentModerator> save(TournamentModerator tournamentModerator);

    /**
     * Delete a tournament moderator assignment.
     */
    Mono<Void> deleteByTournamentIdAndUserId(String tournamentId, String userId);

    /**
     * Delete tournament moderator by ID.
     */
    Mono<Void> deleteById(String id);
}


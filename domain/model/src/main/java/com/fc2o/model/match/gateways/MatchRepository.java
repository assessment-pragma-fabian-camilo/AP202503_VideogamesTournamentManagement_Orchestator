package com.fc2o.model.match.gateways;

import com.fc2o.model.match.Match;
import com.fc2o.model.shared.MatchStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Gateway interface for Match repository operations.
 * Defines all operations needed by match use cases.
 */
public interface MatchRepository {
    /**
     * Find match by ID.
     */
    Mono<Match> findById(String id);

    /**
     * Find all matches.
     */
    Flux<Match> findAll();

    /**
     * Find all matches for a specific tournament.
     */
    Flux<Match> findAllByTournamentId(String tournamentId);

    /**
     * Find all matches for a participant within a tournament.
     */
    Flux<Match> findAllByParticipantIdAndTournamentId(String participantId, String tournamentId);

    /**
     * Find all matches by status.
     */
    Flux<Match> findAllByStatus(MatchStatus status);

    /**
     * Create a new match.
     */
    Mono<Match> save(Match match);

    /**
     * Update an entire match.
     */
    Mono<Match> update(Match match);

    /**
     * Delete match by ID.
     */
    Mono<Match> deleteById(String id);

    Mono<Match> patch(Match match);
}

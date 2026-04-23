package com.fc2o.model.tournament.gateways;

import com.fc2o.model.tournament.Tournament;
import com.fc2o.model.shared.TournamentStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Gateway interface for Tournament repository operations.
 * Defines all operations needed by tournament use cases.
 */
public interface TournamentRepository {
    /**
     * Find tournament by ID.
     */
    Mono<Tournament> findById(String id);

    /**
     * Find all tournaments.
     */
    Flux<Tournament> findAll();

    /**
     * Find all tournaments by promoter ID.
     */
    Flux<Tournament> findAllByPromoterId(String promoterId);

    /**
     * Find all tournaments by status.
     */
    Flux<Tournament> findAllByStatus(TournamentStatus status);

    /**
     * Create a new tournament.
     */
    Mono<Tournament> save(Tournament tournament);

    /**
     * Update an entire tournament.
     */
    Mono<Tournament> update(Tournament tournament);

    /**
     * Delete tournament by ID.
     */
    Mono<Tournament> deleteById(String id);

    Mono<Tournament> patch(Tournament tournament);

    Mono<Tournament> patchRegisterParticipant(String tournamentId, String participantId);

    Mono<Tournament> patchDisqualify(String tournamentId, String participantId);

    Mono<Tournament> patchPreRegisterParticipant(String tournamentId, String participantId);

     Mono<Tournament> patchUnregisterParticipant(String tournamentId, String participantId);
}

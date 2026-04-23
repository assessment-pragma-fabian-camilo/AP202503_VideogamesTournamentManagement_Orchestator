package com.fc2o.model.donation.gateways;

import com.fc2o.model.donation.Donation;
import com.fc2o.model.shared.TransactionStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Gateway interface for Donation repository operations.
 * Defines all operations needed by donation use cases.
 */
public interface DonationRepository {
    /**
     * Find donation by ID.
     */
    Mono<Donation> findById(String id);

    /**
     * Find all donations.
     */
    Flux<Donation> findAll();

    /**
     * Find all donations for a specific tournament.
     */
    Flux<Donation> findAllByTournamentId(String tournamentId);

    /**
     * Find all donations by a specific user.
     */
    Flux<Donation> findAllByUserId(String userId);

    /**
     * Find all donations for a specific team.
     */
    Flux<Donation> findAllByTeamId(String teamId);

    /**
     * Find all donations by status.
     */
    Flux<Donation> findAllByStatus(TransactionStatus status);

    /**
     * Create a new donation.
     */
    Mono<Donation> save(Donation donation);

    /**
     * Update an entire donation.
     */
    Mono<Donation> update(Donation donation);

    /**
     * Delete donation by ID.
     */
    Mono<Donation> deleteById(String id);

    Mono<Donation> patch(Donation donation);
}

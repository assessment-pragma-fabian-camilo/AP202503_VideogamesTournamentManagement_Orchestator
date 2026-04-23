package com.fc2o.model.participationticket.gateways;

import com.fc2o.model.participationticket.ParticipationTicket;
import com.fc2o.model.shared.TicketStatus;
import com.fc2o.model.shared.TransactionStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Gateway interface for ParticipationTicket repository operations.
 * Defines all operations needed by participation ticket use cases.
 */
public interface ParticipationTicketRepository {
    /**
     * Find ticket by ID.
     */
    Mono<ParticipationTicket> findById(String ticketId);

    /**
     * Find all tickets for a specific tournament.
     */
    Flux<ParticipationTicket> findAllByTournamentId(String tournamentId);

    /**
     * Find all tickets for a specific user.
     */
    Flux<ParticipationTicket> findAllByUserId(String userId);

    /**
     * Find all tickets for a specific team.
     */
    Flux<ParticipationTicket> findAllByTeamId(String teamId);

    /**
     * Find all tickets by status.
     */
    Flux<ParticipationTicket> findAllByTicketStatus(TicketStatus status);

    /**
     * Find all tickets by transaction status.
     */
    Flux<ParticipationTicket> findAllByTransactionStatus(TransactionStatus status);

    /**
     * Create a new participation ticket.
     */
    Mono<ParticipationTicket> save(ParticipationTicket participationTicket);

    /**
     * Update an entire participation ticket.
     */
    Mono<ParticipationTicket> update(ParticipationTicket participationTicket);

    /**
     * Delete ticket by ID.
     */
    Mono<Void> deleteById(String ticketId);
}


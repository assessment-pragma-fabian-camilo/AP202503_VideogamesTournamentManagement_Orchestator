package com.fc2o.model.visualizationticket.gateways;

import com.fc2o.model.visualizationticket.VisualizationTicket;
import com.fc2o.model.shared.TicketStatus;
import com.fc2o.model.shared.TransactionStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Gateway interface for VisualizationTicket repository operations.
 * Defines all operations needed by visualization ticket use cases.
 */
public interface VisualizationTicketRepository {
    /**
     * Find ticket by ID.
     */
    Mono<VisualizationTicket> findById(String ticketId);

    /**
     * Find all tickets for a specific tournament.
     */
    Flux<VisualizationTicket> findAllByTournamentId(String tournamentId);

    /**
     * Find all tickets for a specific user.
     */
    Flux<VisualizationTicket> findAllByUserId(String userId);

    /**
     * Find all tickets by status.
     */
    Flux<VisualizationTicket> findAllByTicketStatus(TicketStatus status);

    /**
     * Find all tickets by transaction status.
     */
    Flux<VisualizationTicket> findAllByTransactionStatus(TransactionStatus status);

    /**
     * Create a new visualization ticket.
     */
    Mono<VisualizationTicket> save(VisualizationTicket visualizationTicket);

    /**
     * Update an entire visualization ticket.
     */
    Mono<VisualizationTicket> update(VisualizationTicket visualizationTicket);

    /**
     * Delete ticket by ID.
     */
    Mono<Void> deleteById(String ticketId);
}


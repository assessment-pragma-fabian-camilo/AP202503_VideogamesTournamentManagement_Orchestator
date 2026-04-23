package com.fc2o.usecase.visualizationticket;

import com.fc2o.model.visualizationticket.VisualizationTicket;
import com.fc2o.model.visualizationticket.gateways.VisualizationTicketRepository;
import com.fc2o.model.shared.TransactionStatus;
import reactor.core.publisher.Mono;

/**
 * Use case for updating visualization ticket transaction status.
 */
public class UpdateVisualizationTicketTransactionStatusUseCase {
    private final VisualizationTicketRepository visualizationTicketRepository;

    public UpdateVisualizationTicketTransactionStatusUseCase(
            VisualizationTicketRepository visualizationTicketRepository) {
        this.visualizationTicketRepository = visualizationTicketRepository;
    }

    public Mono<VisualizationTicket> execute(String ticketId, TransactionStatus newStatus) {
        if (ticketId == null || ticketId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Ticket ID is required"));
        }
        if (newStatus == null) {
            return Mono.error(new IllegalArgumentException("New status is required"));
        }

        return visualizationTicketRepository.findById(ticketId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Ticket not found")))
                .flatMap(ticket -> {
                    VisualizationTicket updatedTicket = ticket.toBuilder()
                            .transactionStatus(newStatus)
                            .build();
                    return visualizationTicketRepository.update(updatedTicket);
                });
    }
}


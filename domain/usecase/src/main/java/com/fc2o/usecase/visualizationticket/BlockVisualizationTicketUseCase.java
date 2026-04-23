package com.fc2o.usecase.visualizationticket;

import com.fc2o.model.visualizationticket.VisualizationTicket;
import com.fc2o.model.visualizationticket.gateways.VisualizationTicketRepository;
import com.fc2o.model.shared.TicketStatus;
import reactor.core.publisher.Mono;

/**
 * Use case for blocking a visualization ticket.
 * Business rule: Only PROMOTER or ADMIN can block tickets.
 */
public class BlockVisualizationTicketUseCase {
    private final VisualizationTicketRepository visualizationTicketRepository;

    public BlockVisualizationTicketUseCase(VisualizationTicketRepository visualizationTicketRepository) {
        this.visualizationTicketRepository = visualizationTicketRepository;
    }

    public Mono<VisualizationTicket> execute(String ticketId) {
        if (ticketId == null || ticketId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Ticket ID is required"));
        }

        return visualizationTicketRepository.findById(ticketId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Ticket not found")))
                .flatMap(ticket -> {
                    VisualizationTicket blockedTicket = ticket.toBuilder()
                            .ticketStatus(TicketStatus.BLOCKED)
                            .build();
                    return visualizationTicketRepository.update(blockedTicket);
                });
    }
}


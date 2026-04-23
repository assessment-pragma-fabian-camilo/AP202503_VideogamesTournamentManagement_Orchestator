package com.fc2o.usecase.visualizationticket;

import com.fc2o.model.visualizationticket.VisualizationTicket;
import com.fc2o.model.visualizationticket.gateways.VisualizationTicketRepository;
import reactor.core.publisher.Mono;

/**
 * Use case for retrieving a visualization ticket by ID.
 */
public class GetVisualizationTicketUseCase {
    private final VisualizationTicketRepository visualizationTicketRepository;

    public GetVisualizationTicketUseCase(VisualizationTicketRepository visualizationTicketRepository) {
        this.visualizationTicketRepository = visualizationTicketRepository;
    }

    public Mono<VisualizationTicket> execute(String ticketId) {
        if (ticketId == null || ticketId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Ticket ID is required"));
        }
        return visualizationTicketRepository.findById(ticketId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Ticket not found with ID: " + ticketId)));
    }
}


package com.fc2o.usecase.visualizationticket;

import com.fc2o.model.visualizationticket.VisualizationTicket;
import com.fc2o.model.visualizationticket.gateways.VisualizationTicketRepository;
import reactor.core.publisher.Flux;

/**
 * Use case for listing visualization tickets by tournament.
 */
public class ListVisualizationTicketsUseCase {
    private final VisualizationTicketRepository visualizationTicketRepository;

    public ListVisualizationTicketsUseCase(VisualizationTicketRepository visualizationTicketRepository) {
        this.visualizationTicketRepository = visualizationTicketRepository;
    }

    /**
     * List all visualization tickets for a specific tournament.
     */
    public Flux<VisualizationTicket> executeByTournament(String tournamentId) {
        if (tournamentId == null || tournamentId.isBlank()) {
            return Flux.error(new IllegalArgumentException("Tournament ID is required"));
        }
        return visualizationTicketRepository.findAllByTournamentId(tournamentId);
    }
}


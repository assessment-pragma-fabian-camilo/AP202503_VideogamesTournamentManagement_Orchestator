package com.fc2o.usecase.participationticket;

import com.fc2o.model.participationticket.ParticipationTicket;
import com.fc2o.model.participationticket.gateways.ParticipationTicketRepository;
import reactor.core.publisher.Flux;

/**
 * Use case for listing participation tickets.
 */
public class ListParticipationTicketsUseCase {
    private final ParticipationTicketRepository participationTicketRepository;

    public ListParticipationTicketsUseCase(ParticipationTicketRepository participationTicketRepository) {
        this.participationTicketRepository = participationTicketRepository;
    }

    /**
     * List all tickets for a specific tournament.
     */
    public Flux<ParticipationTicket> executeByTournament(String tournamentId) {
        if (tournamentId == null || tournamentId.isBlank()) {
            return Flux.error(new IllegalArgumentException("Tournament ID is required"));
        }
        return participationTicketRepository.findAllByTournamentId(tournamentId);
    }
}


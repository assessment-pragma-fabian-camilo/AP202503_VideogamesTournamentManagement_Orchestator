package com.fc2o.usecase.participationticket;

import com.fc2o.model.participationticket.ParticipationTicket;
import com.fc2o.model.participationticket.gateways.ParticipationTicketRepository;
import reactor.core.publisher.Mono;

/**
 * Use case for retrieving a participation ticket by ID.
 */
public class GetParticipationTicketUseCase {
    private final ParticipationTicketRepository participationTicketRepository;

    public GetParticipationTicketUseCase(ParticipationTicketRepository participationTicketRepository) {
        this.participationTicketRepository = participationTicketRepository;
    }

    public Mono<ParticipationTicket> execute(String ticketId) {
        if (ticketId == null || ticketId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Ticket ID is required"));
        }
        return participationTicketRepository.findById(ticketId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Ticket not found with ID: " + ticketId)));
    }
}


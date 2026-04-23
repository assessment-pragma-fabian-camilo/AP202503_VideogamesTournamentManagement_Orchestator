package com.fc2o.usecase.participationticket;

import com.fc2o.model.participationticket.ParticipationTicket;
import com.fc2o.model.participationticket.gateways.ParticipationTicketRepository;
import com.fc2o.model.shared.TicketStatus;
import reactor.core.publisher.Mono;

/**
 * Use case for blocking a participation ticket.
 * Business rule: Only PROMOTER or ADMIN can block tickets.
 */
public class BlockParticipationTicketUseCase {
    private final ParticipationTicketRepository participationTicketRepository;

    public BlockParticipationTicketUseCase(ParticipationTicketRepository participationTicketRepository) {
        this.participationTicketRepository = participationTicketRepository;
    }

    public Mono<ParticipationTicket> execute(String ticketId) {
        if (ticketId == null || ticketId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Ticket ID is required"));
        }

        return participationTicketRepository.findById(ticketId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Ticket not found")))
                .flatMap(ticket -> {
                    ParticipationTicket blockedTicket = ticket.toBuilder()
                            .ticketStatus(TicketStatus.BLOCKED)
                            .build();
                    return participationTicketRepository.update(blockedTicket);
                });
    }
}


package com.fc2o.usecase.participationticket;

import com.fc2o.model.participationticket.ParticipationTicket;
import com.fc2o.model.participationticket.gateways.ParticipationTicketRepository;
import com.fc2o.model.shared.TransactionStatus;
import reactor.core.publisher.Mono;

/**
 * Use case for updating participation ticket transaction status.
 */
public class UpdateParticipationTicketTransactionStatusUseCase {
    private final ParticipationTicketRepository participationTicketRepository;

    public UpdateParticipationTicketTransactionStatusUseCase(
            ParticipationTicketRepository participationTicketRepository) {
        this.participationTicketRepository = participationTicketRepository;
    }

    public Mono<ParticipationTicket> execute(String ticketId, TransactionStatus newStatus) {
        if (ticketId == null || ticketId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Ticket ID is required"));
        }
        if (newStatus == null) {
            return Mono.error(new IllegalArgumentException("New status is required"));
        }

        return participationTicketRepository.findById(ticketId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Ticket not found")))
                .flatMap(ticket -> {
                    ParticipationTicket updatedTicket = ticket.toBuilder()
                            .transactionStatus(newStatus)
                            .build();
                    return participationTicketRepository.update(updatedTicket);
                });
    }
}


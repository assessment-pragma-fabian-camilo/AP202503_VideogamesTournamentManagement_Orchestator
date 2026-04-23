package com.fc2o.usecase.participationticket;

import com.fc2o.model.participationticket.ParticipationTicket;
import com.fc2o.model.participationticket.gateways.ParticipationTicketRepository;
import com.fc2o.model.shared.TicketStatus;
import com.fc2o.model.shared.TransactionStatus;
import com.fc2o.usecase.tournamentteam.AutoRegisterTeamInTournamentUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Use case for updating participation ticket status.
 * Transitions: NEW → ACTIVE → USED
 */
@RequiredArgsConstructor
public class UpdateParticipationTicketStatusUseCase {
    private final ParticipationTicketRepository participationTicketRepository;
    private final AutoRegisterTeamInTournamentUseCase autoRegisterTeamInTournamentUseCase;

    public Mono<ParticipationTicket> execute(String ticketId, TicketStatus newStatus) {
        if (ticketId == null || ticketId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Ticket ID is required"));
        }
        if (newStatus == null) {
            return Mono.error(new IllegalArgumentException("New status is required"));
        }

        return participationTicketRepository.findById(ticketId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Ticket not found")))
                .map(ticket -> {
                    if (newStatus.equals(TicketStatus.USED)) {
                        autoRegisterTeamInTournamentUseCase.execute(ticket.teamId(), ticket.tournamentId())
                                .subscribe(); // Fire and forget
                    }
                    return ticket;
                })
                .flatMap(ticket -> {
                    ParticipationTicket updatedTicket = ticket.toBuilder()
                            .ticketStatus(newStatus)
                            .build();
                    return participationTicketRepository.update(updatedTicket);
                });
    }
}


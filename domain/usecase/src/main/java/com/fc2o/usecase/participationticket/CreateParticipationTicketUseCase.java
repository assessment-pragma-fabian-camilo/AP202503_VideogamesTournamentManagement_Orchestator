package com.fc2o.usecase.participationticket;

import com.fc2o.model.participationticket.ParticipationTicket;
import com.fc2o.model.participationticket.gateways.ParticipationTicketRepository;
import com.fc2o.model.tournament.gateways.TournamentRepository;
import com.fc2o.model.user.gateways.UserRepository;
import com.fc2o.model.shared.TicketStatus;
import com.fc2o.model.shared.TransactionStatus;
import reactor.core.publisher.Mono;

/**
 * Use case for creating a participation ticket (purchase).
 * Business rules:
 * - Any user except VIEWER can buy participation tickets
 * - Only for tournaments in UPCOMING or ONGOING status
 * - Tournament cannot have reached place_limit
 * - Initial status is NEW, transaction status is STARTED
 */
public class CreateParticipationTicketUseCase {
    private final ParticipationTicketRepository participationTicketRepository;
    private final TournamentRepository tournamentRepository;
    private final UserRepository userRepository;

    public CreateParticipationTicketUseCase(
            ParticipationTicketRepository participationTicketRepository,
            TournamentRepository tournamentRepository,
            UserRepository userRepository) {
        this.participationTicketRepository = participationTicketRepository;
        this.tournamentRepository = tournamentRepository;
        this.userRepository = userRepository;
    }

    public Mono<ParticipationTicket> execute(
            String tournamentId,
            String userId,
            Double amount,
            String paymentMethod) {

        if (tournamentId == null || tournamentId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Tournament ID is required"));
        }
        if (userId == null || userId.isBlank()) {
            return Mono.error(new IllegalArgumentException("User ID is required"));
        }

        // Verify user exists
        return userRepository.isActiveUser(userId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("User not found")))
                .flatMap(user -> {
                    // Verify tournament exists and is UPCOMING or ONGOING
                    return tournamentRepository.findById(tournamentId)
                            .switchIfEmpty(Mono.error(new IllegalArgumentException("Tournament not found")))
                            .flatMap(tournament -> {
                                if (!tournament.isUpcoming() && !tournament.isOngoing()) {
                                    return Mono.error(new IllegalArgumentException(
                                            "Can only buy tickets for UPCOMING or ONGOING tournaments"));
                                }

                                if (tournament.placeRemaining() <= 0) {
                                    return Mono.error(new IllegalArgumentException(
                                            "Tournament has reached maximum capacity"));
                                }

                                // Create ticket
                                ParticipationTicket newTicket = ParticipationTicket.builder()
                                        .tournamentId(tournamentId)
                                        .userId(userId)
                                        .amount(amount)
                                        .ticketStatus(TicketStatus.NEW)
                                        .transactionStatus(TransactionStatus.STARTED)
                                        .build();

                                return participationTicketRepository.save(newTicket);
                            });
                });
    }
}


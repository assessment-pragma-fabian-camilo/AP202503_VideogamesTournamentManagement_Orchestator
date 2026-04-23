package com.fc2o.usecase.visualizationticket;

import com.fc2o.model.visualizationticket.VisualizationTicket;
import com.fc2o.model.visualizationticket.gateways.VisualizationTicketRepository;
import com.fc2o.model.tournament.gateways.TournamentRepository;
import com.fc2o.model.user.gateways.UserRepository;
import com.fc2o.model.shared.TicketStatus;
import com.fc2o.model.shared.TransactionStatus;
import reactor.core.publisher.Mono;

/**
 * Use case for creating a visualization ticket.
 * Business rules:
 * - Only for PAID tournaments (not FREE)
 * - Only for tournaments in UPCOMING or ONGOING status
 * - Any user can buy visualization tickets
 */
public class CreateVisualizationTicketUseCase {
    private final VisualizationTicketRepository visualizationTicketRepository;
    private final TournamentRepository tournamentRepository;
    private final UserRepository userRepository;

    public CreateVisualizationTicketUseCase(
            VisualizationTicketRepository visualizationTicketRepository,
            TournamentRepository tournamentRepository,
            UserRepository userRepository) {
        this.visualizationTicketRepository = visualizationTicketRepository;
        this.tournamentRepository = tournamentRepository;
        this.userRepository = userRepository;
    }

    public Mono<VisualizationTicket> execute(String tournamentId, String userId, Double amount) {
        if (tournamentId == null || tournamentId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Tournament ID is required"));
        }
        if (userId == null || userId.isBlank()) {
            return Mono.error(new IllegalArgumentException("User ID is required"));
        }

        return userRepository.isActiveUser(userId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("User not found")))
                .flatMap(user -> tournamentRepository.findById(tournamentId)
                        .switchIfEmpty(Mono.error(new IllegalArgumentException("Tournament not found")))
                        .flatMap(tournament -> {
                            if (!tournament.isPaid()) {
                                return Mono.error(new IllegalArgumentException(
                                        "Visualization tickets can only be bought for PAID tournaments"));
                            }
                            if (!tournament.isUpcoming() && !tournament.isOngoing()) {
                                return Mono.error(new IllegalArgumentException(
                                        "Can only buy tickets for UPCOMING or ONGOING tournaments"));
                            }

                            VisualizationTicket newTicket = VisualizationTicket.builder()
                                    .tournamentId(tournamentId)
                                    .userId(userId)
                                    .amount(amount)
                                    .ticketStatus(TicketStatus.NEW)
                                    .transactionStatus(TransactionStatus.STARTED)
                                    .build();

                            return visualizationTicketRepository.save(newTicket);
                        }));
    }
}


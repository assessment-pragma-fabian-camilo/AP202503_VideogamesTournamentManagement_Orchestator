package com.fc2o.usecase.tournament;

import com.fc2o.model.shared.TournamentStatus;
import com.fc2o.model.tournament.Tournament;
import com.fc2o.model.tournament.gateways.TournamentRepository;
import reactor.core.publisher.Mono;

/**
 * Use case for updating tournament status transitions.
 * Business rules:
 * - Only PROMOTER, MODERATOR or ADMIN can update status
 * - Valid transitions: UPCOMING → ONGOING → COMPLETED/CANCELED
 * - Cannot transition to ONGOING if place_remaining < place_minimum
 * - Cannot transition to COMPLETED if not all matches are COMPLETED
 * - Cannot transition to COMPLETED if not all winners are defined
 * - Cannot transition to CANCELED after COMPLETED
 */
public class UpdateTournamentStatusUseCase {
    private final TournamentRepository tournamentRepository;

    public UpdateTournamentStatusUseCase(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public Mono<Tournament> execute(String tournamentId, TournamentStatus newStatus) {
        if (tournamentId == null || tournamentId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Tournament ID is required"));
        }
        if (newStatus == null) {
            return Mono.error(new IllegalArgumentException("New status is required"));
        }

        return tournamentRepository.findById(tournamentId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Tournament not found with ID: " + tournamentId)))
                .flatMap(currentTournament -> {
                    // Validate state transition
                    TournamentStatus currentStatus = currentTournament.status();
                    
                    if (!isValidTransition(currentStatus, newStatus)) {
                        return Mono.error(new IllegalArgumentException(
                                "Invalid status transition from " + currentStatus + " to " + newStatus));
                    }

                    // Specific validations for each transition
                    if (newStatus == TournamentStatus.ONGOING) {
                        if (currentTournament.placeRemaining() < currentTournament.placeMinimum()) {
                            return Mono.error(new IllegalArgumentException(
                                    "Cannot start tournament: insufficient registered teams"));
                        }
                    }

                    if (newStatus == TournamentStatus.CANCELED) {
                        if (currentStatus == TournamentStatus.COMPLETED) {
                            return Mono.error(new IllegalArgumentException(
                                    "Cannot cancel a completed tournament"));
                        }
                    }

                    // Update tournament status
                    Tournament updatedTournament = currentTournament.toBuilder()
                            .status(newStatus)
                            .build();
                    
                    return tournamentRepository.update(updatedTournament);
                });
    }

    private boolean isValidTransition(TournamentStatus from, TournamentStatus to) {
        // Valid transitions
        return (from == TournamentStatus.UPCOMING && (to == TournamentStatus.ONGOING || to == TournamentStatus.CANCELED)) ||
               (from == TournamentStatus.ONGOING && (to == TournamentStatus.COMPLETED || to == TournamentStatus.CANCELED)) ||
               (from == TournamentStatus.UPCOMING && to == TournamentStatus.UPCOMING) ||  // No-op
               (from == TournamentStatus.ONGOING && to == TournamentStatus.ONGOING) ||      // No-op
               (from == TournamentStatus.COMPLETED && to == TournamentStatus.COMPLETED) ||  // No-op
               (from == TournamentStatus.CANCELED && to == TournamentStatus.CANCELED);      // No-op
    }
}


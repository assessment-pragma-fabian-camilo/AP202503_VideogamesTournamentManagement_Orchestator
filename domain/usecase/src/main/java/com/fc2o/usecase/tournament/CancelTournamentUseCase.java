package com.fc2o.usecase.tournament;

import com.fc2o.model.shared.TournamentStatus;
import com.fc2o.model.tournament.Tournament;
import com.fc2o.model.tournament.gateways.TournamentRepository;
import reactor.core.publisher.Mono;

/**
 * Use case for canceling a tournament.
 * Business rules:
 * - Only PROMOTER, MODERATOR or ADMIN can cancel
 * - Can only cancel if status is UPCOMING or ONGOING
 * - Cannot cancel if already COMPLETED or CANCELED
 */
public class CancelTournamentUseCase {
    private final TournamentRepository tournamentRepository;

    public CancelTournamentUseCase(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }
    
    public Mono<Tournament> execute(String tournamentId) {
        if (tournamentId == null || tournamentId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Tournament ID is required"));
        }

        return tournamentRepository.findById(tournamentId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Tournament not found with ID: " + tournamentId)))
                .flatMap(tournament -> {
                    TournamentStatus status = tournament.status();

                    if (status == TournamentStatus.COMPLETED || status == TournamentStatus.CANCELED) {
                        return Mono.error(new IllegalArgumentException("Cannot cancel a tournament in status: " + status));
                    }

                    Tournament canceledTournament = tournament.toBuilder()
                            .status(TournamentStatus.CANCELED)
                            .build();

                    return tournamentRepository.update(canceledTournament);
                });
    }
}


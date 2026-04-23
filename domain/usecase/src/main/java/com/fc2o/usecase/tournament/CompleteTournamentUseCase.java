package com.fc2o.usecase.tournament;

import com.fc2o.model.shared.TournamentStatus;
import com.fc2o.model.tournament.Tournament;
import com.fc2o.model.tournament.gateways.TournamentRepository;
import reactor.core.publisher.Mono;

/**
 * Use case for completing a tournament.
 * Business rules:
 * - Only PROMOTER, MODERATOR or ADMIN can complete tournaments
 * - Tournament must be in ONGOING status
 * - All matches must be COMPLETED
 * - All winners must be defined
 */
public class CompleteTournamentUseCase {
    private final TournamentRepository tournamentRepository;

    public CompleteTournamentUseCase(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public Mono<Tournament> execute(String tournamentId) {
        if (tournamentId == null || tournamentId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Tournament ID is required"));
        }

        return tournamentRepository.findById(tournamentId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Tournament not found with ID: " + tournamentId)))
                .flatMap(tournament -> {
                    // This will be enhanced with match validation from Match repository
                    // For now, we validate basic state
                    if (!tournament.isOngoing()) {
                        return Mono.error(new IllegalArgumentException(
                                "Cannot complete tournament: tournament is not in ONGOING status"));
                    }

                    return Mono.just(tournament.toBuilder()
                            .status(TournamentStatus.COMPLETED)
                            .build())
                            .flatMap(tournamentRepository::update);
                });
    }
}


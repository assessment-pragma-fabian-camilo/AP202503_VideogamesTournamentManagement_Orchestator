package com.fc2o.usecase.tournament;

import com.fc2o.model.shared.TournamentStatus;
import com.fc2o.model.tournament.Tournament;
import com.fc2o.model.tournament.gateways.TournamentRepository;
import reactor.core.publisher.Mono;

/**
 * Use case for updating tournament place limits.
 * Business rule: Only PROMOTER or ADMIN can modify place_limit and place_minimum,
 * and only for tournaments in status UPCOMING.
 */
public class UpdatePlaceLimitsUseCase {
    private final TournamentRepository tournamentRepository;

    public UpdatePlaceLimitsUseCase(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public Mono<Tournament> execute(String tournamentId, Short newPlaceLimit, Short newPlaceMinimum) {
        if (tournamentId == null || tournamentId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Tournament ID is required"));
        }
        if (newPlaceLimit == null || newPlaceLimit <= 0) {
            return Mono.error(new IllegalArgumentException("Place limit must be greater than 0"));
        }
        if (newPlaceMinimum == null || newPlaceMinimum <= 0) {
            return Mono.error(new IllegalArgumentException("Place minimum must be greater than 0"));
        }
        if (newPlaceLimit < newPlaceMinimum) {
            return Mono.error(new IllegalArgumentException("Place limit must be >= place minimum"));
        }

        return tournamentRepository.findById(tournamentId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Tournament not found with ID: " + tournamentId)))
                .flatMap(tournament -> {
                    if (tournament.status() != TournamentStatus.UPCOMING) {
                        return Mono.error(new IllegalArgumentException(
                                "Can only modify place limits for UPCOMING tournaments"));
                    }

                    Tournament updatedTournament = tournament.toBuilder()
                            .placeLimit(newPlaceLimit)
                            .placeMinimum(newPlaceMinimum)
                            .build();
                    
                    return tournamentRepository.update(updatedTournament);
                });
    }
}


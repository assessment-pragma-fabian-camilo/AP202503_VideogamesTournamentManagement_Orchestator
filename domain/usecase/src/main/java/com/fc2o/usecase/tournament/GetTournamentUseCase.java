package com.fc2o.usecase.tournament;

import com.fc2o.model.tournament.Tournament;
import com.fc2o.model.tournament.gateways.TournamentRepository;
import reactor.core.publisher.Mono;

/**
 * Use case for retrieving a tournament by ID.
 */
public class GetTournamentUseCase {
    private final TournamentRepository tournamentRepository;

    public GetTournamentUseCase(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public Mono<Tournament> execute(String tournamentId) {
        if (tournamentId == null || tournamentId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Tournament ID is required"));
        }
        return tournamentRepository.findById(tournamentId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Tournament not found with ID: " + tournamentId)));
    }
}


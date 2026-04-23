package com.fc2o.usecase.tournamentmoderator;

import com.fc2o.model.tournamentmoderator.TournamentModerator;
import com.fc2o.model.tournamentmoderator.gateways.TournamentModeratorRepository;
import reactor.core.publisher.Flux;

/**
 * Use case for retrieving all moderators of a tournament.
 */
public class GetModeratorsUseCase {
    private final TournamentModeratorRepository tournamentModeratorRepository;

    public GetModeratorsUseCase(TournamentModeratorRepository tournamentModeratorRepository) {
        this.tournamentModeratorRepository = tournamentModeratorRepository;
    }

    public Flux<TournamentModerator> execute(String tournamentId) {
        if (tournamentId == null || tournamentId.isBlank()) {
            return Flux.error(new IllegalArgumentException("Tournament ID is required"));
        }
        return tournamentModeratorRepository.findAllByTournamentId(tournamentId);
    }
}


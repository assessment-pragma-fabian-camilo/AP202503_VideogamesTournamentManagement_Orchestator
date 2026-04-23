package com.fc2o.usecase.tournamentmoderator;

import com.fc2o.model.tournamentmoderator.gateways.TournamentModeratorRepository;
import reactor.core.publisher.Mono;

/**
 * Use case for verifying if a user is a moderator of a specific tournament.
 */
public class VerifyModeratorPermissionUseCase {
    private final TournamentModeratorRepository tournamentModeratorRepository;

    public VerifyModeratorPermissionUseCase(TournamentModeratorRepository tournamentModeratorRepository) {
        this.tournamentModeratorRepository = tournamentModeratorRepository;
    }

    public Mono<Boolean> execute(String tournamentId, String userId) {
        if (tournamentId == null || tournamentId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Tournament ID is required"));
        }
        if (userId == null || userId.isBlank()) {
            return Mono.error(new IllegalArgumentException("User ID is required"));
        }

        return tournamentModeratorRepository.existsByTournamentIdAndUserId(tournamentId, userId);
    }
}


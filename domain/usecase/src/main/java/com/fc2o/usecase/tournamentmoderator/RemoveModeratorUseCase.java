package com.fc2o.usecase.tournamentmoderator;

import com.fc2o.model.tournamentmoderator.gateways.TournamentModeratorRepository;
import reactor.core.publisher.Mono;

/**
 * Use case for removing a moderator from a tournament.
 * Business rule: Only PROMOTER or ADMIN can remove moderators.
 */
public class RemoveModeratorUseCase {
    private final TournamentModeratorRepository tournamentModeratorRepository;

    public RemoveModeratorUseCase(TournamentModeratorRepository tournamentModeratorRepository) {
        this.tournamentModeratorRepository = tournamentModeratorRepository;
    }

    public Mono<Void> execute(String tournamentId, String userId) {
        if (tournamentId == null || tournamentId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Tournament ID is required"));
        }
        if (userId == null || userId.isBlank()) {
            return Mono.error(new IllegalArgumentException("User ID is required"));
        }

        return tournamentModeratorRepository.deleteByTournamentIdAndUserId(tournamentId, userId);
    }
}


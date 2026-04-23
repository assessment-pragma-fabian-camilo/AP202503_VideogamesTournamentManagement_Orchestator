package com.fc2o.usecase.team;

import com.fc2o.model.team.gateways.TeamRepository;
import reactor.core.publisher.Mono;

/**
 * Use case for deleting a team.
 * Business rule: Only ADMIN can delete teams.
 * A team cannot be deleted if it has active participation in tournaments.
 */
public class DeleteTeamUseCase {
    private final TeamRepository teamRepository;

    public DeleteTeamUseCase(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Mono<Void> execute(String teamId) {
        if (teamId == null || teamId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Team ID is required"));
        }
        return teamRepository.deleteById(teamId);
    }
}


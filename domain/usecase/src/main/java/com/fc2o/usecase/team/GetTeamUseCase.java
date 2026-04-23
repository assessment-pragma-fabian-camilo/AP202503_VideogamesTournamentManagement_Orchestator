package com.fc2o.usecase.team;

import com.fc2o.model.team.Team;
import com.fc2o.model.team.gateways.TeamRepository;
import reactor.core.publisher.Mono;

/**
 * Use case for retrieving a team by ID.
 */
public class GetTeamUseCase {
    private final TeamRepository teamRepository;

    public GetTeamUseCase(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Mono<Team> execute(String teamId) {
        if (teamId == null || teamId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Team ID is required"));
        }
        return teamRepository.findById(teamId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Team not found with ID: " + teamId)));
    }
}


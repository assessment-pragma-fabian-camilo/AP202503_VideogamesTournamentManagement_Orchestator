package com.fc2o.usecase.team;

import com.fc2o.model.team.Team;
import com.fc2o.model.team.gateways.TeamRepository;
import reactor.core.publisher.Flux;

/**
 * Use case for listing all teams or teams filtered by leader user ID.
 */
public class ListTeamsUseCase {
    private final TeamRepository teamRepository;

    public ListTeamsUseCase(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    /**
     * List all teams in the system.
     */
    public Flux<Team> executeAll() {
        return teamRepository.findAll();
    }

    /**
     * List teams led by a specific user.
     */
    public Flux<Team> executeByLeader(String leaderUserId) {
        if (leaderUserId == null || leaderUserId.isBlank()) {
            return Flux.error(new IllegalArgumentException("Leader user ID is required"));
        }
        return teamRepository.findByLeaderUserId(leaderUserId);
    }
}


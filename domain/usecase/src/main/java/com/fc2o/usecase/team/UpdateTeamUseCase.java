package com.fc2o.usecase.team;

import com.fc2o.model.team.Team;
import com.fc2o.model.team.gateways.TeamRepository;
import reactor.core.publisher.Mono;

/**
 * Use case for updating team information.
 * Business rule: Only the team leader, PROMOTER or ADMIN can update a team.
 */
public class UpdateTeamUseCase {
    private final TeamRepository teamRepository;

    public UpdateTeamUseCase(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Mono<Team> execute(String teamId, String teamName) {
        if (teamId == null || teamId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Team ID is required"));
        }
        if (teamName == null || teamName.isBlank()) {
            return Mono.error(new IllegalArgumentException("Team name is required"));
        }

        return teamRepository.findById(teamId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Team not found with ID: " + teamId)))
                .flatMap(currentTeam -> {
                    Team updatedTeam = currentTeam.toBuilder()
                            .name(teamName)
                            .build();
                    return teamRepository.save(updatedTeam);
                });
    }
}


package com.fc2o.usecase.team;

import com.fc2o.model.team.gateways.TeamMemberRepository;
import reactor.core.publisher.Mono;

/**
 * Use case for removing a member from a team.
 * Business rule: Only PROMOTER, MODERATOR or ADMIN can remove members.
 */
public class RemoveTeamMemberUseCase {
    private final TeamMemberRepository teamMemberRepository;

    public RemoveTeamMemberUseCase(TeamMemberRepository teamMemberRepository) {
        this.teamMemberRepository = teamMemberRepository;
    }

    public Mono<Void> execute(String teamId, String userId) {
        if (teamId == null || teamId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Team ID is required"));
        }
        if (userId == null || userId.isBlank()) {
            return Mono.error(new IllegalArgumentException("User ID is required"));
        }

        return teamMemberRepository.deleteByTeamIdAndUserId(teamId, userId);
    }
}


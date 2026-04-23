package com.fc2o.usecase.team;

import com.fc2o.model.team.TeamMember;
import com.fc2o.model.team.gateways.TeamMemberRepository;
import reactor.core.publisher.Flux;

/**
 * Use case for retrieving all members of a team.
 */
public class GetTeamMembersUseCase {
    private final TeamMemberRepository teamMemberRepository;

    public GetTeamMembersUseCase(TeamMemberRepository teamMemberRepository) {
        this.teamMemberRepository = teamMemberRepository;
    }

    public Flux<TeamMember> execute(String teamId) {
        if (teamId == null || teamId.isBlank()) {
            return Flux.error(new IllegalArgumentException("Team ID is required"));
        }
        return teamMemberRepository.findAllByTeamId(teamId);
    }
}


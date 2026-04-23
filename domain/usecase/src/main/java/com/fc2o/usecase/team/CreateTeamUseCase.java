package com.fc2o.usecase.team;

import com.fc2o.model.team.Team;
import com.fc2o.model.team.TeamMember;
import com.fc2o.model.team.gateways.TeamRepository;
import com.fc2o.model.team.gateways.TeamMemberRepository;
import com.fc2o.model.user.gateways.UserRepository;
import reactor.core.publisher.Mono;

/**
 * Use case for creating a new team.
 * Business rules:
 * - A team must have a leader who is a user of the platform
 * - Only users with role PLAYER can create teams
 * - The leader must exist in the system
 */
public class CreateTeamUseCase {
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final UserRepository userRepository;

    public CreateTeamUseCase(
            TeamRepository teamRepository,
            TeamMemberRepository teamMemberRepository,
            UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.userRepository = userRepository;
    }

    public Mono<Team> execute(String teamName, String leaderUserId) {
        if (teamName == null || teamName.isBlank()) {
            return Mono.error(new IllegalArgumentException("Team name is required"));
        }
        if (leaderUserId == null || leaderUserId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Leader user ID is required"));
        }

        // Verify that leader user exists
        return userRepository.isActiveUser(leaderUserId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Leader user not found with ID: " + leaderUserId)))
                .flatMap(leader -> {
                    // Create the team
                    Team newTeam = Team.builder()
                            .name(teamName)
                            .leaderUserId(leaderUserId)
                            .build();

                    return teamRepository.save(newTeam)
                            .flatMap(savedTeam -> {
                                // Add leader as first team member
                                TeamMember leaderMember = TeamMember.builder()
                                        .teamId(savedTeam.id())
                                        .userId(leaderUserId)
                                        .build();

                                return teamMemberRepository.save(leaderMember)
                                        .then(Mono.just(savedTeam));
                            });
                });
    }
}


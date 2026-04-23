package com.fc2o.usecase.team;

import com.fc2o.model.team.TeamMember;
import com.fc2o.model.team.gateways.TeamMemberRepository;
import com.fc2o.model.user.gateways.UserRepository;
import reactor.core.publisher.Mono;

/**
 * Use case for adding a member to a team.
 * Business rules:
 * - A user can only be added once to a team
 * - Only PROMOTER, MODERATOR or ADMIN can add members
 * - The team must be inscribed in at least one tournament with ACTIVE/USED status
 */
public class AddTeamMemberUseCase {
    private final TeamMemberRepository teamMemberRepository;
    private final UserRepository userRepository;

    public AddTeamMemberUseCase(
            TeamMemberRepository teamMemberRepository,
            UserRepository userRepository) {
        this.teamMemberRepository = teamMemberRepository;
        this.userRepository = userRepository;
    }

    public Mono<TeamMember> execute(String teamId, String userId) {
        if (teamId == null || teamId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Team ID is required"));
        }
        if (userId == null || userId.isBlank()) {
            return Mono.error(new IllegalArgumentException("User ID is required"));
        }

        // Verify user exists
        return userRepository.isActiveUser(userId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("User not found with ID: " + userId)))
                .flatMap(user -> {
                    // Check if user is already member of this team
                    return teamMemberRepository.existsByTeamIdAndUserId(teamId, userId)
                            .flatMap(exists -> {
                                if (exists) {
                                    return Mono.error(new IllegalArgumentException("User is already a member of this team"));
                                }

                                // Add new team member
                                TeamMember newMember = TeamMember.builder()
                                        .teamId(teamId)
                                        .userId(userId)
                                        .build();

                                return teamMemberRepository.save(newMember);
                            });
                });
    }
}


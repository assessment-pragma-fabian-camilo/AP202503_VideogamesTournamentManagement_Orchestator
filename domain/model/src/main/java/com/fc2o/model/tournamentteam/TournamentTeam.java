package com.fc2o.model.tournamentteam;

import lombok.Builder;

/**
 * Represents a team registered in a specific tournament.
 * This is the bridge between tournaments and teams, allowing teams to participate in tournaments.
 */
@Builder(toBuilder = true)
public record TournamentTeam(
        String id,
        String createdAt,
        String tournamentId,
        String teamId
) {
}


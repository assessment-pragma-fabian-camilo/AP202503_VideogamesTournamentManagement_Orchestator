package com.fc2o.api.dto.tournamentteam;

import lombok.Builder;

@Builder
public record CreateTournamentTeamRequest(
        String tournamentId,
        String teamId) {
}


package com.fc2o.api.dto.request.tournamentteam;

import lombok.Builder;

@Builder
public record CreateTournamentTeamRequest(
        String tournamentId,
        String teamId) {
}


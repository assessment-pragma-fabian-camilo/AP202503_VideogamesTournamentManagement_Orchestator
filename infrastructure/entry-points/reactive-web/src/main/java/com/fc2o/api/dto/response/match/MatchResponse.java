package com.fc2o.api.dto.response.match;

import com.fc2o.model.shared.MatchStatus;

import java.util.Set;

public record MatchResponse(
        String id,
        String createdAt,
        String startDateTime,
        String endDateTime,
        String tournamentId,
        String winnerTeamId,
        MatchStatus status,
        String matchDetails,
        Set<String> participantIds
) {
}


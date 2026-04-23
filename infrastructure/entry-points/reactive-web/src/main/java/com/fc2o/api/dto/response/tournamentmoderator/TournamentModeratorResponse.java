package com.fc2o.api.dto.response.tournamentmoderator;

public record TournamentModeratorResponse(
        String id,
        String createdAt,
        String tournamentId,
        String userId
) {
}


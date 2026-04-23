package com.fc2o.model.tournamentmoderator;

import lombok.Builder;

@Builder(toBuilder = true)
public record TournamentModerator(
        String id,
        String createdAt,
        String tournamentId,
        String userId
) {
}


package com.fc2o.model.matchteam;

import lombok.Builder;

@Builder(toBuilder = true)
public record MatchTeam(
        String id,
        String createdAt,
        String matchId,
        String teamId
) {
}


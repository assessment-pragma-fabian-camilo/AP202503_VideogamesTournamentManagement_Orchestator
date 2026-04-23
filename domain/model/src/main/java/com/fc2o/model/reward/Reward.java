package com.fc2o.model.reward;

import lombok.Builder;

@Builder(toBuilder = true)
public record Reward(
        String id,
        String createdAt,
        String tournamentId,
        String teamId,
        Short position,
        Double prize
) {

    public boolean hasTeam() {
        return teamId != null;
    }

    public boolean hasPrize() {
        return prize != null;
    }
}

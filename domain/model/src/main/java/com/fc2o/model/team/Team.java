package com.fc2o.model.team;

import lombok.Builder;

@Builder(toBuilder = true)
public record Team(
        String id,
        String createdAt,
        String name,
        String leaderUserId
) {
    public boolean isLeader(String userId) {
        return leaderUserId != null && leaderUserId.equals(userId);
    }
}


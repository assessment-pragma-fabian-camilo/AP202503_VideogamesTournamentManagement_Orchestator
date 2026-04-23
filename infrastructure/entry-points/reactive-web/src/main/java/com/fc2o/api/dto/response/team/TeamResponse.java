package com.fc2o.api.dto.response.team;

public record TeamResponse(
        String id,
        String createdAt,
        String name,
        String leaderUserId
) {
}


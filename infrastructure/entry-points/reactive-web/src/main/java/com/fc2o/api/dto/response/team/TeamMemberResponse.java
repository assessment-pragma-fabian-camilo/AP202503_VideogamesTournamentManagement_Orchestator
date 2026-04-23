package com.fc2o.api.dto.response.team;

public record TeamMemberResponse(
        String id,
        String createdAt,
        String teamId,
        String userId
) {
}


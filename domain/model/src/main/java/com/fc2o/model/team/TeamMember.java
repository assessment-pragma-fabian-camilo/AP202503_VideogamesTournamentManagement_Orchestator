package com.fc2o.model.team;

import lombok.Builder;

@Builder(toBuilder = true)
public record TeamMember(
        String id,
        String createdAt,
        String teamId,
        String userId
) {
}


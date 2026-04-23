package com.fc2o.api.dto.request.team;

import jakarta.validation.constraints.NotBlank;

public record AddTeamMemberRequest(
        @NotBlank(message = "User id is required")
        String userId
) {
}


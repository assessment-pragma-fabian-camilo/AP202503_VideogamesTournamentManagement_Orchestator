package com.fc2o.api.dto.request.match;

import jakarta.validation.constraints.NotBlank;

public record AssignTeamToMatchRequest(
        @NotBlank(message = "Team id is required")
        String teamId
) {
}


package com.fc2o.api.dto.request.reward;

import jakarta.validation.constraints.NotBlank;

public record AssignRewardToTeamRequest(
        @NotBlank(message = "Team id is required")
        String teamId
) {
}


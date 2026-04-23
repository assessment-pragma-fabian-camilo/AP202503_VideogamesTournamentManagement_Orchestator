package com.fc2o.api.dto.request.team;

import jakarta.validation.constraints.NotBlank;

public record CreateTeamRequest(
        @NotBlank(message = "Name is required")
        String name
) {
}


package com.fc2o.api.dto.request.team;

import jakarta.validation.constraints.NotBlank;

public record UpdateTeamRequest(
        @NotBlank(message = "Name is required")
        String name
) {
}


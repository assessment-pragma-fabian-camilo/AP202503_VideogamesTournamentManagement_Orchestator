package com.fc2o.api.dto.request.game;

import jakarta.validation.constraints.NotBlank;

public record CreateGameRequest(
        @NotBlank(message = "Name is required")
        String name
) {
}


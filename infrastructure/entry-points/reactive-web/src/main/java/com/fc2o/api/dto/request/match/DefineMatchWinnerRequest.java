package com.fc2o.api.dto.request.match;

import jakarta.validation.constraints.NotBlank;

public record DefineMatchWinnerRequest(
        @NotBlank(message = "Winner team id is required")
        String winnerTeamId
) {
}


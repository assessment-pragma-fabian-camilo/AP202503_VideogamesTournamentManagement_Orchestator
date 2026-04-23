package com.fc2o.api.dto.request.tournamentmoderator;

import jakarta.validation.constraints.NotBlank;

public record AssignModeratorRequest(
        @NotBlank(message = "Tournament id is required")
        String tournamentId,

        @NotBlank(message = "User id is required")
        String userId
) {
}


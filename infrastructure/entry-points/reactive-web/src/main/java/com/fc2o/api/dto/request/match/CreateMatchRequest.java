package com.fc2o.api.dto.request.match;

import jakarta.validation.constraints.NotBlank;

public record CreateMatchRequest(
        @NotBlank(message = "Tournament id is required")
        String tournamentId,

        @NotBlank(message = "Date start is required")
        String dateStart,

        String timeStart
) {
}


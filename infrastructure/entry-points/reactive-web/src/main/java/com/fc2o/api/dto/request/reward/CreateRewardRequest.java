package com.fc2o.api.dto.request.reward;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateRewardRequest(
    @NotBlank(message = "Tournament id is required")
    String tournamentId,

    @Positive(message = "Position must be greater than zero")
    Short position,

    @PositiveOrZero(message = "Prize must be greater than or equal to zero")
    Double prize
) {
}


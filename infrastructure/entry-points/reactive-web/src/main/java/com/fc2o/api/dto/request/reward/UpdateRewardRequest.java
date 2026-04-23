package com.fc2o.api.dto.request.reward;

import jakarta.validation.constraints.PositiveOrZero;

public record UpdateRewardRequest(
        @PositiveOrZero(message = "Prize must be greater than or equal to zero")
        Double prize
) {
}


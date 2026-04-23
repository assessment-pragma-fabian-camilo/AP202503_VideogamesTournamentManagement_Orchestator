package com.fc2o.api.dto.request.tournament;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

public record UpdatePlaceLimitsRequest(
        @Valid @Positive
        Short placeLimit,

        @Valid @Positive
        Short placeMinimum
) {
}

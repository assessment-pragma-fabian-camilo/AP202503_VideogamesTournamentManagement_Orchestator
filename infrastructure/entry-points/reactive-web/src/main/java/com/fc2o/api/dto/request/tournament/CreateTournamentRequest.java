package com.fc2o.api.dto.request.tournament;

import com.fc2o.model.shared.CommissionType;
import jakarta.validation.constraints.*;

public record CreateTournamentRequest(
        @NotBlank(message = "Name is required")
        String name,

        String rules,

        @NotBlank(message = "Game id is required")
        String gameId,

        @Positive(message = "Place limit must be greater than zero")
        Short placeLimit,

        @Positive(message = "Place minimum must be greater than zero")
        Short placeMinimum,

        @NotBlank(message = "Start date is required")
        String dateStart,

        @NotBlank(message = "End date is required")
        String dateEnd,

        @PositiveOrZero(message = "Participation price must be greater than or equal to zero")
        Double participationPrice,

        @PositiveOrZero(message = "Visualization price must be greater than or equal to zero")
        Double visualizationPrice,

        @NotNull(message = "Type is required")
        CommissionType type,

        String description
) {
}


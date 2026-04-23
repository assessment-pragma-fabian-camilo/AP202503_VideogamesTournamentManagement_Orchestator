package com.fc2o.api.dto.request.commission;

import com.fc2o.model.shared.CommissionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateCommissionRequest(
        @NotNull(message = "Commission type is required")
        CommissionType commissionType,

        @NotNull(message = "Participation percentage is required")
        @PositiveOrZero(message = "Participation percentage must be greater than or equal to zero")
        Float participationPercentage,

        @NotNull(message = "Visualization percentage is required")
        @PositiveOrZero(message = "Visualization percentage must be greater than or equal to zero")
        Float visualizationPercentage,

        @NotNull(message = "Donation percentage is required")
        @PositiveOrZero(message = "Donation percentage must be greater than or equal to zero")
        Float donationPercentage
) {
}


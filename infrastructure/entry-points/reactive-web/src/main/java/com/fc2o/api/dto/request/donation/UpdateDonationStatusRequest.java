package com.fc2o.api.dto.request.donation;

import com.fc2o.model.shared.TransactionStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateDonationStatusRequest(
        @NotNull(message = "Status is required")
        TransactionStatus status
) {
}


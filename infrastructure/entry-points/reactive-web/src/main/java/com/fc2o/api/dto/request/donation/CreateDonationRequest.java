package com.fc2o.api.dto.request.donation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateDonationRequest(
        @NotBlank(message = "Tournament id is required")
        String tournamentId,

        String teamId,

        @Positive(message = "Amount must be greater than zero")
        Double amount,

        String message
) {
}


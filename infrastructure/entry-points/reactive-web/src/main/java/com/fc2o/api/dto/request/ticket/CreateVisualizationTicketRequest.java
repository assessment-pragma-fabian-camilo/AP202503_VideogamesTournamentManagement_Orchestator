package com.fc2o.api.dto.request.ticket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateVisualizationTicketRequest(
        @NotBlank(message = "Tournament id is required")
        String tournamentId,

        @Positive(message = "Amount must be greater than zero")
        Double amount
) {
}


package com.fc2o.api.dto.request.ticket;

import com.fc2o.model.shared.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateParticipationTicketRequest(
        @NotBlank(message = "Tournament id is required")
        String tournamentId,

        @Positive(message = "Amount must be greater than zero")
        Double amount,

        @NotNull(message = "Payment method is required")
        PaymentMethod paymentMethod
) {
}


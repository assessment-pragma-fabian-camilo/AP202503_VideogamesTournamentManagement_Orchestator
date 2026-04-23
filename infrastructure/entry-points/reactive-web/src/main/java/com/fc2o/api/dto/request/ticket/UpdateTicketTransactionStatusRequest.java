package com.fc2o.api.dto.request.ticket;

import com.fc2o.model.shared.TransactionStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateTicketTransactionStatusRequest(
        @NotNull(message = "Transaction status is required")
        TransactionStatus status
) {
}


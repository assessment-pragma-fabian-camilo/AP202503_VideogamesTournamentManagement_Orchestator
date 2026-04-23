package com.fc2o.api.dto.request.ticket;

import com.fc2o.model.shared.TicketStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateTicketStatusRequest(
        @NotNull(message = "Status is required")
        TicketStatus status
) {
}


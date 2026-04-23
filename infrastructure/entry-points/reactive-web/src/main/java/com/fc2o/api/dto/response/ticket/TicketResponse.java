package com.fc2o.api.dto.response.ticket;

import com.fc2o.model.shared.PaymentMethod;
import com.fc2o.model.shared.TicketStatus;
import com.fc2o.model.shared.TransactionStatus;

public record TicketResponse(
        String id,
        String createdAt,
        String qr,
        TicketStatus ticketStatus,
        TransactionStatus transactionStatus,
        PaymentMethod paymentMethod,
        String tournamentId,
        String userId,
        String teamId,
        Double amount
) {
}


package com.fc2o.api.dto.response.donation;

import com.fc2o.model.shared.PaymentMethod;
import com.fc2o.model.shared.TransactionStatus;

public record DonationResponse(
        String id,
        String createdAt,
        String tournamentId,
        String userId,
        String teamId,
        Double amount,
        String message,
        TransactionStatus status,
        PaymentMethod paymentMethod
) {
}


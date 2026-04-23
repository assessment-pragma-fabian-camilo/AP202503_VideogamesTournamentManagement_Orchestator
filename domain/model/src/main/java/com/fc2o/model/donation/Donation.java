package com.fc2o.model.donation;

import com.fc2o.model.shared.TransactionStatus;
import com.fc2o.model.shared.PaymentMethod;
import lombok.Builder;

@Builder(toBuilder = true)
public record Donation(
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
    public Boolean isApproved() {
        return status == TransactionStatus.APPROVED;
    }

    public Boolean isRejected() {
        return status == TransactionStatus.REJECTED;
    }

    public Boolean isInProcess() {
        return status == TransactionStatus.IN_PROCESS;
    }
}

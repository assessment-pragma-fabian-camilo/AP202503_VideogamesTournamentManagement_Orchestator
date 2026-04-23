package com.fc2o.model.visualizationticket;

import com.fc2o.model.shared.PaymentMethod;
import com.fc2o.model.shared.TicketStatus;
import com.fc2o.model.shared.TransactionStatus;
import lombok.Builder;

@Builder(toBuilder = true)
public record VisualizationTicket(
        String id,
        String createdAt,
        String tournamentId,
        String userId,
        byte[] qr,
        TransactionStatus transactionStatus,
        TicketStatus ticketStatus,
        PaymentMethod paymentMethod,
        Double amount
) {
    public boolean isNew() {
        return ticketStatus == TicketStatus.NEW;
    }

    public boolean isActive() {
        return ticketStatus == TicketStatus.ACTIVE;
    }

    public boolean isUsed() {
        return ticketStatus == TicketStatus.USED;
    }

    public boolean isBlocked() {
        return ticketStatus == TicketStatus.BLOCKED;
    }

    public boolean isApproved() {
        return transactionStatus == TransactionStatus.APPROVED;
    }

    public boolean isInProcess() {
        return transactionStatus == TransactionStatus.IN_PROCESS;
    }

    public boolean isRejected() {
        return transactionStatus == TransactionStatus.REJECTED;
    }
}


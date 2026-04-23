package com.fc2o.model.participationticket;

import com.fc2o.model.shared.PaymentMethod;
import com.fc2o.model.shared.TicketStatus;
import com.fc2o.model.shared.TransactionStatus;
import lombok.Builder;

@Builder(toBuilder = true)
public record ParticipationTicket(
        String id,
        String createdAt,
        byte[] qr,
        TicketStatus ticketStatus,
        TransactionStatus transactionStatus,
        PaymentMethod paymentMethod,
        String tournamentId,
        String userId,
        String teamId,
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


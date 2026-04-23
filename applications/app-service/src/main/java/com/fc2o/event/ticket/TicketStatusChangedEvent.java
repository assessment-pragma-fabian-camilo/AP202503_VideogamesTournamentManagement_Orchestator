package com.fc2o.event.ticket;

import java.time.Instant;

/**
 * Event published when ticket status changes.
 */
public record TicketStatusChangedEvent(
    String ticketId,
    String ticketStatus,
    String transactionStatus,
    String userId,
    Double amount,
    Instant timestamp
) {}


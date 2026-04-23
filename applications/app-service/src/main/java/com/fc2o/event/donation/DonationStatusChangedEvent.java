package com.fc2o.event.donation;

import java.time.Instant;

/**
 * Event published when donation status changes.
 */
public record DonationStatusChangedEvent(
    String donationId,
    String status,
    String userId,
    Double amount,
    Instant timestamp
) {}


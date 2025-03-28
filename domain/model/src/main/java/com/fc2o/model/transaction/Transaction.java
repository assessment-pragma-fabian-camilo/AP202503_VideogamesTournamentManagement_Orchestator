package com.fc2o.model.transaction;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder(toBuilder = true)
public record Transaction(
  UUID id,
  UUID customerId,
  UUID tournamentId,
  Status status,
  LocalDateTime createdTime,
  PaymentMethod paymentMethod,
  String reference,
  Type type
) {
  public Boolean isApproved() {
    return status().equals(Status.APPROVED);
  }

  public Boolean isRejected() {
    return status().equals(Status.REJECTED);
  }

  public Boolean isInProcess() {
    return status().equals(Status.IN_PROCESS);
  }

  public Boolean isStarted() {
    return status().equals(Status.STARTED);
  }
}

package com.fc2o.model.transaction;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder(toBuilder = true)
public record Transaction(
  UUID id,
  UUID buyerId,
  UUID tournamentId,
  Status status,
  LocalDateTime createdTime,
  PaymentMethod paymentMethod,
  String reference,
  Type type
) {
}

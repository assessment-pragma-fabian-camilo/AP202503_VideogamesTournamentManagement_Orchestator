package com.fc2o.model.ticket;
import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record Ticket(
  UUID id,
  UUID buyerId,
  UUID tournamentId,
  UUID transactionId,
  Type type
) {
}

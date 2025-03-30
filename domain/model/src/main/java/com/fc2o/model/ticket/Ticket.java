package com.fc2o.model.ticket;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
public record Ticket(
  String id,
  LocalDateTime createdTime,
  String customerId,
  String tournamentId,
  String transactionId,
  Type type,
  Status status,
  String qr
) {
  public Boolean isParticipationType() {
    return type().equals(Type.PARTICIPATION);
  }

  public Boolean isVisualizationType() {
    return type().equals(Type.VISUALIZATION);
  }

  public Boolean isNew() {
    return status().equals(Status.NEW);
  }

  public Boolean isUsed() {
    return status().equals(Status.USED);
  }

  public Boolean isBlocked() {
    return status().equals(Status.BLOCKED);
  }
}

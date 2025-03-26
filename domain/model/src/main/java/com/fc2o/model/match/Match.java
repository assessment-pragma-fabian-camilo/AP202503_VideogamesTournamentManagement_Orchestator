package com.fc2o.model.match;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Builder(toBuilder = true)
public record Match(
  UUID id,
  Set<UUID> participantIds,
  UUID winnerId,
  UUID tournamentId,
  LocalDateTime timeStart,
  LocalDate dateStart,
  LocalDateTime timeEnd,
  Status status
) {
  //NOT_STARTED, IN_PROGRESS, FINISHED, CANCELED
  public Boolean isNotStarted() {
    return status.equals(Status.NOT_STARTED);
  }

  public Boolean isInProgress() {
    return status.equals(Status.IN_PROGRESS);
  }

  public Boolean isFinished() {
    return status.equals(Status.FINISHED);
  }

  public Boolean isCanceled() {
    return status.equals(Status.CANCELED);
  }
}

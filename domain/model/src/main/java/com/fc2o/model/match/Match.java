package com.fc2o.model.match;
import lombok.Builder;


import java.util.Set;

@Builder(toBuilder = true)
public record Match(
  String id,
  String createdTime,
  Set<String> participantIds,
  String winnerId,
  String tournamentId,
  String timeStart,
  String dateStart,
  String timeEnd,
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

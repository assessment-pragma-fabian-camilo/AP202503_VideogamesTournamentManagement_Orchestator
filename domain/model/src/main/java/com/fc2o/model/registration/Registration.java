package com.fc2o.model.registration;
import lombok.Builder;


@Builder(toBuilder = true)
public record Registration(
  String id,
  String createdTime,
  String tournamentId,
  String participantId,
  Status status
) {
}

package com.fc2o.model.registration;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder(toBuilder = true)
public record Registration(
  UUID id,
  UUID tournamentId,
  UUID teamId,
  LocalDateTime createdTime,
  Status status
) {
}

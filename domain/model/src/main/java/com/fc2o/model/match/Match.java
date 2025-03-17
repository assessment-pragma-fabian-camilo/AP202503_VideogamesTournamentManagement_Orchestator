package com.fc2o.model.match;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Builder(toBuilder = true)
public record Match(
  UUID id,
  Set<UUID> teamIds,
  UUID winnerTeamId,
  UUID tournamentId,
  LocalDateTime timeStart,
  LocalDateTime timeEnd,
  Status status
) {
}

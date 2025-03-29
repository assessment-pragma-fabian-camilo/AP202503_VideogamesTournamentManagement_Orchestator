package com.fc2o.api.dto.match.res.start;

import com.fc2o.api.dto.match.common.StatusDto;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
public record MatchDto(
  UUID id,
  Set<UUID> participantIds,
  UUID tournamentId,
  LocalDate dateStart,
  LocalDateTime timeStart,
  StatusDto status
) {
}

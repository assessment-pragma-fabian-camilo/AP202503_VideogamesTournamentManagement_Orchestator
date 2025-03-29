package com.fc2o.api.dto.match.res.register;

import com.fc2o.api.dto.match.common.StatusDto;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Builder
public record MatchDto(
  UUID id,
  Set<UUID> participantIds,
  UUID tournamentId,
  LocalDate dateStart,
  StatusDto status
) {
}

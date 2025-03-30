package com.fc2o.api.dto.match.res.start;

import com.fc2o.api.dto.match.common.StatusDto;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;


@Builder
public record MatchDto(
  String id,
  Set<String> participantIds,
  String tournamentId,
  LocalDate dateStart,
  LocalDateTime timeStart,
  StatusDto status
) {
}

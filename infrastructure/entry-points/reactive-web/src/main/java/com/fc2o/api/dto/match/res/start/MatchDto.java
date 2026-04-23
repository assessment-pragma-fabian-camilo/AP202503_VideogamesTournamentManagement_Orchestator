package com.fc2o.api.dto.match.res.start;

import com.fc2o.api.dto.match.common.StatusDto;
import lombok.Builder;


import java.util.Set;


@Builder
public record MatchDto(
  String id,
  Set<String> participantIds,
  String tournamentId,
  String dateStart,
  String timeStart,
  StatusDto status
) {
}

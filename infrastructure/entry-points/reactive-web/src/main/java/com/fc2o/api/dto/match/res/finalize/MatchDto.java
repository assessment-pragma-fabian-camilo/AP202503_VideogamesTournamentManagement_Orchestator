package com.fc2o.api.dto.match.res.finalize;

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
  String timeEnd,
  StatusDto status
) {
}

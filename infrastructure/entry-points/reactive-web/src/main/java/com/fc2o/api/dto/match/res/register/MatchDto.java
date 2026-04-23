package com.fc2o.api.dto.match.res.register;

import com.fc2o.api.dto.match.common.StatusDto;
import lombok.Builder;


import java.util.Set;


@Builder
public record MatchDto(
  String id,
  Set<String> participantIds,
  String tournamentId,
  String dateStart,
  StatusDto status
) {
}

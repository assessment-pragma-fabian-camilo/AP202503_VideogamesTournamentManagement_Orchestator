package com.fc2o.api.dto.tournament.res.start;

import com.fc2o.api.dto.tournament.common.StatusDto;
import lombok.Builder;



@Builder
public record TournamentDto(
  String id,
  StatusDto status
) {
}

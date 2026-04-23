package com.fc2o.api.dto.tournament.res.register;

import com.fc2o.api.dto.tournament.common.StatusDto;
import lombok.Builder;



@Builder
public record TournamentDto(
  String id,
  String createdTime,
  StatusDto status
) {
}

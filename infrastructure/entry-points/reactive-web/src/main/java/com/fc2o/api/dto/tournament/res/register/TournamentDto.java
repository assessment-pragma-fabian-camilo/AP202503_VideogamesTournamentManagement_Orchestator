package com.fc2o.api.dto.tournament.res.register;

import com.fc2o.api.dto.tournament.common.StatusDto;
import lombok.Builder;

import java.util.UUID;

@Builder
public record TournamentDto(
  UUID id,
  StatusDto status
) {
}

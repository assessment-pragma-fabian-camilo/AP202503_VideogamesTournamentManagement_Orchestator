package com.fc2o.api.dto.tournament.req.register;

import lombok.Builder;

@Builder
public record RegisterTournamentRequestDto(
  TournamentDto tournament
) {
}

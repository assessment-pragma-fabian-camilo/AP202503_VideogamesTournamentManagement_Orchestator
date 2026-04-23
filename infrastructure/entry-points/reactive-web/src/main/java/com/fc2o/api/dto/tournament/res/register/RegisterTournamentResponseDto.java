package com.fc2o.api.dto.tournament.res.register;

import lombok.Builder;

@Builder
public record RegisterTournamentResponseDto(
  TournamentDto tournament
) {
}

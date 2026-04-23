package com.fc2o.api.dto.tournament.res.start;

import lombok.Builder;

@Builder
public record StartTournamentResponseDto(
  TournamentDto tournament
) {
}

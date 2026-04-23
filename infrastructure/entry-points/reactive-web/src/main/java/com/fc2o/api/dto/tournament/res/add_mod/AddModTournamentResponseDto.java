package com.fc2o.api.dto.tournament.res.add_mod;

import lombok.Builder;

@Builder
public record AddModTournamentResponseDto(
  TournamentDto tournament
) {
}

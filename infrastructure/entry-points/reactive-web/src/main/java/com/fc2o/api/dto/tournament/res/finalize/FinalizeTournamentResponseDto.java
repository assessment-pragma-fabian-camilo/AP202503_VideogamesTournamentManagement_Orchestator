package com.fc2o.api.dto.tournament.res.finalize;

import lombok.Builder;

@Builder
public record FinalizeTournamentResponseDto(
  TournamentDto tournament,
  RewardDto reward
) {
}

package com.fc2o.api.dto.tournament.req.finalize;

import lombok.Builder;

@Builder
public record FinalizeTournamentRequestDto(
  RewardDto reward
) {
}

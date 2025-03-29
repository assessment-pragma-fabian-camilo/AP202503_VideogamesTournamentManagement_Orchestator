package com.fc2o.api.dto.tournament.req.cancel;

import lombok.Builder;

@Builder
public record CancelTournamentRequestDto(
  TournamentDto tournament
) {
}

package com.fc2o.api.dto.tournament.res.reschedule;

import lombok.Builder;

@Builder
public record RescheduleTournamentResponseDto(
  TournamentDto tournament
) {
}

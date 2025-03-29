package com.fc2o.api.dto.tournament.res.reschedule;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record TournamentDto(
  UUID id,
  LocalDate dateStart,
  LocalDate dateEnd
) {
}

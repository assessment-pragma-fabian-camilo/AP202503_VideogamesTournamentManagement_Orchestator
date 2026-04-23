package com.fc2o.api.dto.tournament.res.reschedule;

import lombok.Builder;

import java.time.LocalDate;


@Builder
public record TournamentDto(
  String id,
  LocalDate dateStart,
  LocalDate dateEnd
) {
}

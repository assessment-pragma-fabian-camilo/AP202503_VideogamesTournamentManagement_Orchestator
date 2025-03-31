package com.fc2o.api.dto.tournament.res.reschedule;

import lombok.Builder;




@Builder
public record TournamentDto(
  String id,
  String dateStart,
  String dateEnd
) {
}

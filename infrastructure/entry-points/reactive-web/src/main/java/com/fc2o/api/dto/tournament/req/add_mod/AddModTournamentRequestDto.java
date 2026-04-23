package com.fc2o.api.dto.tournament.req.add_mod;

import lombok.Builder;
import lombok.Singular;

import java.util.Set;


@Builder
public record AddModTournamentRequestDto(
  @Singular
  Set<String> moderatorIds
) {
}

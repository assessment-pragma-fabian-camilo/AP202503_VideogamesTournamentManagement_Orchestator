package com.fc2o.api.dto.tournament.res.add_mod;

import lombok.Builder;
import lombok.Singular;

import java.util.Set;


@Builder
public record TournamentDto(
  String id,
  @Singular
  Set<String> moderatorIds
) {
}

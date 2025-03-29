package com.fc2o.api.dto.tournament.res.add_mod;

import lombok.Builder;
import lombok.Singular;

import java.util.Set;
import java.util.UUID;

@Builder
public record TournamentDto(
  UUID id,
  @Singular
  Set<UUID> moderatorIds
) {
}

package com.fc2o.api.dto.tournament.req.add_mod;

import lombok.Builder;
import lombok.Singular;

import java.util.Set;
import java.util.UUID;

@Builder
public record AddModTournamentRequestDto(
  @Singular
  Set<UUID> moderatorIds
) {
}

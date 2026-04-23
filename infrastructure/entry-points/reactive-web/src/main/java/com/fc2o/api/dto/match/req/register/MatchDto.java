package com.fc2o.api.dto.match.req.register;

import lombok.Builder;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Builder
public record MatchDto(
  Set<UUID> participantIds,
  LocalDate dateStart
) {
}

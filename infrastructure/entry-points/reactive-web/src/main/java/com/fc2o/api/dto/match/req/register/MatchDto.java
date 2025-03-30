package com.fc2o.api.dto.match.req.register;

import lombok.Builder;

import java.time.LocalDate;
import java.util.Set;


@Builder
public record MatchDto(
  Set<String> participantIds,
  LocalDate dateStart
) {
}

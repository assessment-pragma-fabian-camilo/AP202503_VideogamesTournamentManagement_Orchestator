package com.fc2o.api.dto.match.req.register;

import lombok.Builder;


import java.util.Set;


@Builder
public record MatchDto(
  Set<String> participantIds,
  String dateStart
) {
}

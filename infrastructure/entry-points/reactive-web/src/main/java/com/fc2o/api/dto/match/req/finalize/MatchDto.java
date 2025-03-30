package com.fc2o.api.dto.match.req.finalize;

import lombok.Builder;



@Builder
public record MatchDto(
  String winnerId
) {
}

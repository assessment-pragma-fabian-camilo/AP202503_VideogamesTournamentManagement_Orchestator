package com.fc2o.api.dto.match.req.register;

import lombok.Builder;

@Builder
public record RegisterMatchRequestDto(
  MatchDto match
) {
}

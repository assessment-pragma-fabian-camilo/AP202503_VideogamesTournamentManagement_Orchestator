package com.fc2o.api.dto.match.res.register;

import lombok.Builder;

@Builder
public record RegisterMatchResponseDto(
  MatchDto match
) {
}

package com.fc2o.api.dto.match.res.start;

import lombok.Builder;

@Builder
public record StartMatchResponseDto(
  MatchDto match
) {
}

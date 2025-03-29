package com.fc2o.api.dto.match.res.cancel;

import lombok.Builder;

@Builder
public record CancelMatchResponseDto(
  MatchDto match
) {
}

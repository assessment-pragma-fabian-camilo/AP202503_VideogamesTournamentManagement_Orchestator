package com.fc2o.api.dto.match.res.finalize;

import lombok.Builder;

@Builder
public record FinalizeMatchResponseDto(
  MatchDto match
) {
}

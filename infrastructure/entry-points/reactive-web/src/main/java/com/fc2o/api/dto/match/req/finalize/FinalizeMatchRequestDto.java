package com.fc2o.api.dto.match.req.finalize;

import lombok.Builder;

@Builder
public record FinalizeMatchRequestDto(
  MatchDto match
) {
}

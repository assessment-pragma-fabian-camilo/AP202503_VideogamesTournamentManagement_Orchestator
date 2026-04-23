package com.fc2o.api.dto.match.req.finalize;

import lombok.Builder;

import java.util.UUID;

@Builder
public record MatchDto(
  UUID winnerId
) {
}

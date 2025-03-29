package com.fc2o.api.dto.tournament.req.finalize;

import lombok.Builder;

import java.util.Map;
import java.util.UUID;

@Builder
public record RewardDto(
  Map<Byte, UUID> standings
) {
}

package com.fc2o.api.dto.tournament.res.finalize;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Builder
public record RewardDto(
  UUID id,
  Map<Byte, UUID> standings,
  Map<UUID, BigDecimal> prizePerStanding
) {
}
package com.fc2o.model.reward;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Builder(toBuilder = true)
public record Reward(
  UUID id,
  Map<Byte, UUID> standings,
  Map<UUID, BigDecimal> prizePerStanding,
  UUID tournamentId
) {
}

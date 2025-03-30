package com.fc2o.api.dto.tournament.res.finalize;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.Map;


@Builder
public record RewardDto(
  String id,
  Map<Byte, String> standings,
  Map<String, BigDecimal> prizePerStanding
) {
}
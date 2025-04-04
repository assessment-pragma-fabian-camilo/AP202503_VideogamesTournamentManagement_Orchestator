package com.fc2o.model.reward;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Map;

@Builder(toBuilder = true)
public record Reward(
  String id,
  String createdTime,
  Map<Byte, String> standings,
  Map<String, BigDecimal> prizePerStanding,
  String tournamentId
) {
}

package com.fc2o.airtable.reward.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.Map;

@Builder
public record FieldsDto(
  Map<Byte, String> standings,
  Map<String, BigDecimal> prizePerStanding,
  String tournamentId
) {
}

package com.fc2o.airtable.donation.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record FieldsDto(
  String userId,
  String tournamentId,
  BigDecimal amount
) {
}

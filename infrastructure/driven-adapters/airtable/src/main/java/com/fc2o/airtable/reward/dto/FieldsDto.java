package com.fc2o.airtable.reward.dto;

import lombok.Builder;

@Builder
public record FieldsDto(
  String standings,
  String prizePerStanding,
  String tournamentId
) {
}

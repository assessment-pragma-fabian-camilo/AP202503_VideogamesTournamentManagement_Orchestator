package com.fc2o.airtable.registration.dto;

import lombok.Builder;

@Builder
public record FieldsDto(
  String tournamentId,
  String participantId,
  StatusDto status
) {
}

package com.fc2o.airtable.registration.dto;

import com.fc2o.model.registration.Status;
import lombok.Builder;

@Builder
public record FieldsDto(
  String tournamentId,
  String participantId,
  StatusDto status
) {
}

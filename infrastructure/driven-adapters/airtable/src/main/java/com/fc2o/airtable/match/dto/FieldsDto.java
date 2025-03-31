package com.fc2o.airtable.match.dto;

import lombok.Builder;


import java.util.Set;

@Builder
public record FieldsDto(
  Set<String> participantIds,
  String winnerId,
  String tournamentId,
  String timeStart,
  String timeEnd,
  String dateStart,
  StatusDto status
) {
}

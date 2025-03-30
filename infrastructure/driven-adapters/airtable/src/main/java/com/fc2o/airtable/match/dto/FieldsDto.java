package com.fc2o.airtable.match.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record FieldsDto(
  Set<String> participantIds,
  String winnerId,
  String tournamentId,
  LocalDateTime timeStart,
  LocalDateTime timeEnd,
  LocalDate dateStart,
  StatusDto status
) {
}

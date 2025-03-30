package com.fc2o.airtable.transaction.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record RecordDto(
  String id,
  LocalDateTime createdTime,
  FieldsDto fields
) {
}

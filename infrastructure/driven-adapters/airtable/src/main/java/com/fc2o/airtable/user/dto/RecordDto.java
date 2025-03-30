package com.fc2o.airtable.user.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record RecordDto(
  String id,
  LocalDateTime createdTime,
  FieldsDto fields
) {
}

package com.fc2o.airtable.registration.dto;

import lombok.Builder;


@Builder
public record RecordDto(
  String id,
  String createdTime,
  FieldsDto fields
) {
}

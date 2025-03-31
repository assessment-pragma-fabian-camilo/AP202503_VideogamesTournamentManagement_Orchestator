package com.fc2o.airtable.tournament.dto;

import lombok.Builder;


@Builder
public record RecordDto(
  String id,
  String createdTime,
  FieldsDto fields
) {
}

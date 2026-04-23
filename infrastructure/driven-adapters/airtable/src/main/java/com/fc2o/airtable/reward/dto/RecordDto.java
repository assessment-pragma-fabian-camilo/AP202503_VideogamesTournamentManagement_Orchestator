package com.fc2o.airtable.reward.dto;

import lombok.Builder;


@Builder
public record RecordDto(
  String id,
  String createdTime,
  FieldsDto fields
) {
}

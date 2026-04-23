package com.fc2o.airtable.transaction.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record WrapperDto(
  List<RecordDto> records,
  String offset
) {
}

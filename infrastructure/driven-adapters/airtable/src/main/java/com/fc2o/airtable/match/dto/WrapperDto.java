package com.fc2o.airtable.match.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record WrapperDto(
  List<RecordDto> records,
  String offset,
  Boolean typecast
) {
}

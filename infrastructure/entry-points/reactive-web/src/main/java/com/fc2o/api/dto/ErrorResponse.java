package com.fc2o.api.dto;

import lombok.Builder;

@Builder
public record ErrorResponse(
  String dateTime,
  String errorMessage
) {
}

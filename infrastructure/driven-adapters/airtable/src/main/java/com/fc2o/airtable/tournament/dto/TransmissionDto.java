package com.fc2o.airtable.tournament.dto;

import lombok.Builder;

@Builder
public record TransmissionDto(
  String url,
  PlatformDto platform
) {
}

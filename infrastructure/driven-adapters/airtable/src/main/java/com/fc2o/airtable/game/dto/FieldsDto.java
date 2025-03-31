package com.fc2o.airtable.game.dto;

import lombok.Builder;


import java.util.Set;

@Builder
public record FieldsDto(
  String name,
  String description,
  Set<String> categories,
  Set<ConsoleDto> consoles,
  String releaseDate,
  ClassificationDto classification
) {
}

package com.fc2o.model.game;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Builder(toBuilder = true)
public record Game(
  String id,
  LocalDateTime createdTime,
  String name,
  String description,
  Set<String> categories,
  Set<Console> consoles,
  LocalDate releaseDate,
  Classification classification
) {
}

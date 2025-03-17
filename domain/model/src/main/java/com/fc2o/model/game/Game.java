package com.fc2o.model.game;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Builder(toBuilder = true)
public record Game(
  UUID id,
  String name,
  String description,
  Set<Category> categories,
  Set<Console> consoles,
  LocalDate releaseDate,
  Classification classification
) {
}

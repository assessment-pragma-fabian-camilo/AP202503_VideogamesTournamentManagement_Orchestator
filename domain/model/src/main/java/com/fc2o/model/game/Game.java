package com.fc2o.model.game;
import lombok.Builder;


import java.util.Set;

@Builder(toBuilder = true)
public record Game(
  String id,
  String createdTime,
  String name,
  String description,
  Set<String> categories,
  Set<Console> consoles,
  String releaseDate,
  Classification classification
) {
}

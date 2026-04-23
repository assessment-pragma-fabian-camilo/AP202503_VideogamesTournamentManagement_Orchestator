package com.fc2o.model.game;

import lombok.Builder;

@Builder(toBuilder = true)
public record Category(
  String name,
  String description
) {
}

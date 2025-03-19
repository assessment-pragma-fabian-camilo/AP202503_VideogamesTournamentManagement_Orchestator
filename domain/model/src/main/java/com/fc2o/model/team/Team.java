package com.fc2o.model.team;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder(toBuilder = true)
public record Team(
  UUID id,
  String name,
  LocalDateTime createdTime
) {
}

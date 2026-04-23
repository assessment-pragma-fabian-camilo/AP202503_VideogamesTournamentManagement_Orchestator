package com.fc2o.model.tournament;

import lombok.Builder;

@Builder
public record Transmission(
  String url,
  Platform platform
) {
}

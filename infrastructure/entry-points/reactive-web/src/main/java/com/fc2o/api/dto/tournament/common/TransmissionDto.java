package com.fc2o.api.dto.tournament.common;

import com.fc2o.model.tournament.Platform;
import lombok.Builder;

@Builder
public record TransmissionDto(
  String url,
  Platform platform
) {
}

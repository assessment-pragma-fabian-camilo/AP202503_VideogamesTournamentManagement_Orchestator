package com.fc2o.api.dto.match.res.cancel;

import com.fc2o.api.dto.match.common.StatusDto;
import lombok.Builder;

import java.util.UUID;

@Builder
public record MatchDto(
  UUID id,
  StatusDto status
) {
}

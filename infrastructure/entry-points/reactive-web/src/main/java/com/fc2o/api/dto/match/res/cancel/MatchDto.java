package com.fc2o.api.dto.match.res.cancel;

import com.fc2o.api.dto.match.common.StatusDto;
import lombok.Builder;



@Builder
public record MatchDto(
  String id,
  StatusDto status
) {
}

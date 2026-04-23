package com.fc2o.api.dto.tournament.req.finalize;

import lombok.Builder;

import java.util.Map;


@Builder
public record RewardDto(
  Map<Byte, String> standings
) {
}

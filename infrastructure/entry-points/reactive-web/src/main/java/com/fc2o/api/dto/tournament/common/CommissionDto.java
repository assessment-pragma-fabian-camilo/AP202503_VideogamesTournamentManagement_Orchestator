package com.fc2o.api.dto.tournament.common;

import lombok.Builder;

@Builder
public record CommissionDto(
  Float viewPercentage,
  Float participationPercentage,
  Float DonationPercentage
) {
}

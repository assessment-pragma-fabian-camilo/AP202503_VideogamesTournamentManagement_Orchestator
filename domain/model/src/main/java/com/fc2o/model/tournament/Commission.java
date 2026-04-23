package com.fc2o.model.tournament;

import lombok.Builder;

@Builder
public record Commission(
  Float viewPercentage,
  Float participationPercentage,
  Float DonationPercentage
) {
}

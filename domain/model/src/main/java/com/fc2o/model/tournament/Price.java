package com.fc2o.model.tournament;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record Price(
  BigDecimal participation,
  BigDecimal visualization
) {
}

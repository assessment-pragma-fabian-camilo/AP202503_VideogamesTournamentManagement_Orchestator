package com.fc2o.api.dto.tournament.common;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PriceDto(
  BigDecimal participation,
  BigDecimal view
) {
}

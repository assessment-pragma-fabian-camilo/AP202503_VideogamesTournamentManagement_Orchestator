package com.fc2o.model.tournament;

import java.math.BigDecimal;

public record Price(
  BigDecimal participation,
  BigDecimal view
) {
}

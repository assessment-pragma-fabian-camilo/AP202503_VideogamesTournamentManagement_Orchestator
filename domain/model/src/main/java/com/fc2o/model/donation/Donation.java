package com.fc2o.model.donation;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder(toBuilder = true)
public record Donation(
  String id,
  LocalDateTime createdTime,
  String userId,
  String tournamentId,
  BigDecimal amount
) {
}

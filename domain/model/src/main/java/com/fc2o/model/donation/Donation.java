package com.fc2o.model.donation;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder(toBuilder = true)
public record Donation(
  UUID id,
  UUID userId,
  UUID tournamentId,
  BigDecimal amount,
  LocalDateTime createdTime
) {
}

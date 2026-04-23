package com.fc2o.model.donation;
import lombok.Builder;

import java.math.BigDecimal;

@Builder(toBuilder = true)
public record Donation(
  String id,
  String createdTime,
  String userId,
  String tournamentId,
  String transactionId,
  BigDecimal amount
) {
}

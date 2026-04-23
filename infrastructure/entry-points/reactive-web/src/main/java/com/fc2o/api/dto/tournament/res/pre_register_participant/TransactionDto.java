package com.fc2o.api.dto.tournament.res.pre_register_participant;

import lombok.Builder;

@Builder
public record TransactionDto(
  String id,
  String createdTime,
  String reference,
  String tournamentId,
  String customerId,
  StatusDto status,
  TypeDto type
) {
}

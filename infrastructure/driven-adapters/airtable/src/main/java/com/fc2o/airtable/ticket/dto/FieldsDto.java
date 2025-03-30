package com.fc2o.airtable.ticket.dto;

import lombok.Builder;

@Builder
public record FieldsDto(
  String customerId,
  String tournamentId,
  String transactionId,
  TypeDto type,
  StatusDto status,
  String qr
) {
}

package com.fc2o.airtable.transaction.dto;

import lombok.Builder;

@Builder
public record FieldsDto(
  String reference,
  String customerId,
  String tournamentId,
  StatusDto status,
  PaymentMethodDto paymentMethod,
  TypeDto type
) {
}

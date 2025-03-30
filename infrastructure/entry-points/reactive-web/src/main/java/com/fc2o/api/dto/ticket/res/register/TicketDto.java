package com.fc2o.api.dto.ticket.res.register;

import com.fc2o.api.dto.ticket.common.StatusDto;
import com.fc2o.api.dto.ticket.common.TypeDto;
import lombok.Builder;



@Builder
public record TicketDto(
  String id,
  String customerId,
  String tournamentId,
  String transactionId,
  StatusDto status,
  TypeDto type
) {
}

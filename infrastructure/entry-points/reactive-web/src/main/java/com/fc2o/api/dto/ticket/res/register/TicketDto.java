package com.fc2o.api.dto.ticket.res.register;

import com.fc2o.api.dto.ticket.common.StatusDto;
import com.fc2o.api.dto.ticket.common.TypeDto;
import lombok.Builder;

import java.util.UUID;

@Builder
public record TicketDto(
  UUID id,
  UUID customerId,
  UUID tournamentId,
  UUID transactionId,
  StatusDto status,
  TypeDto type
) {
}

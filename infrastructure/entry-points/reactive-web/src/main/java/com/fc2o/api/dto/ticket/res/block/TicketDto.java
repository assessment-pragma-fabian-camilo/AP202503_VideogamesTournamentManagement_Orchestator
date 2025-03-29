package com.fc2o.api.dto.ticket.res.block;

import com.fc2o.api.dto.ticket.common.StatusDto;

import java.util.UUID;

public record TicketDto(
  UUID id,
  StatusDto status
) {
}

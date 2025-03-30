package com.fc2o.api.dto.ticket.res.block;

import com.fc2o.api.dto.ticket.common.StatusDto;



public record TicketDto(
  String id,
  StatusDto status
) {
}

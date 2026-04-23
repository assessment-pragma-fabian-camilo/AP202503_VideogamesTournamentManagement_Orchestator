package com.fc2o.api.dto.ticket.res.register;

import lombok.Builder;

@Builder
public record RegisterTicketResponseDto(
  TicketDto ticket
) {
}

package com.fc2o.api.dto.ticket.res.block;

import lombok.Builder;

@Builder
public record BlockTicketResponseDto(
  TicketDto ticket
) {
}

package com.fc2o.usecase.ticket.crud;

import com.fc2o.model.ticket.Ticket;
import com.fc2o.model.ticket.gateways.TicketRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateTicketUseCase {
  private final TicketRepository ticketRepository;

  public Mono<Ticket> create(Ticket ticket) {
    return ticketRepository.save(ticket);
  }
}

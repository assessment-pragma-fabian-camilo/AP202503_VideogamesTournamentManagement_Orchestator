package com.fc2o.model.ticket.gateways;

import com.fc2o.model.ticket.Ticket;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TicketRepository {
  Mono<Ticket> findById(UUID id);
  Flux<Ticket> findAll();
  Mono<Ticket> save(Ticket ticket);
  Mono<Ticket> update(Ticket ticket);
  Mono<Ticket> patch(Ticket ticket);
  Mono<Ticket> deleteById(UUID id);
}

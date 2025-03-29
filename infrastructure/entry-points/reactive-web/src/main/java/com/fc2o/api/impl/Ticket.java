package com.fc2o.api.impl;

import com.fc2o.model.ticket.gateways.TicketRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class Ticket implements TicketRepository {
  @Override
  public Mono<com.fc2o.model.ticket.Ticket> findById(UUID id) {
    return null;
  }

  @Override
  public Flux<com.fc2o.model.ticket.Ticket> findAllByTournamentIdAndCustomerId(UUID tournamentId, UUID customerId) {
    return null;
  }

  @Override
  public Flux<com.fc2o.model.ticket.Ticket> findAll() {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.ticket.Ticket> save(com.fc2o.model.ticket.Ticket ticket) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.ticket.Ticket> update(com.fc2o.model.ticket.Ticket ticket) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.ticket.Ticket> patch(com.fc2o.model.ticket.Ticket ticket) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.ticket.Ticket> deleteById(UUID id) {
    return null;
  }
}

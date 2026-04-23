package com.fc2o.model.ticket.gateways;

import com.fc2o.model.ticket.Ticket;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface TicketRepository {
  Mono<Ticket> findById(String id);
  Flux<Ticket> findAllByTournamentIdAndCustomerId(String tournamentId, String customerId);
  Flux<Ticket> findAll();
  Mono<Ticket> save(Ticket ticket);
  Mono<Ticket> update(Ticket ticket);
  Mono<Ticket> patch(Ticket ticket);
  Mono<Ticket> deleteById(String id);
}

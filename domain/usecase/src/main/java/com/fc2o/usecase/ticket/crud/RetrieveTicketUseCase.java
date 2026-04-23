package com.fc2o.usecase.ticket.crud;

import com.fc2o.model.ticket.Ticket;
import com.fc2o.model.ticket.gateways.TicketRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RetrieveTicketUseCase {
  private final TicketRepository ticketRepository;
  public Mono<Ticket> retrieveTicketById(String id) {
    return ticketRepository.findById(id);
  }

  public Flux<Ticket> retrieveTicketsByTournamentIdAndCustomerId(String tournamentId, String customerId) {
    return ticketRepository.findAllByTournamentIdAndCustomerId(tournamentId, customerId);
  }
}

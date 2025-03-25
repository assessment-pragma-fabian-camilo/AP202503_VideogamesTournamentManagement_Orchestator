package com.fc2o.usecase.ticket.crud;

import com.fc2o.model.ticket.Ticket;
import com.fc2o.model.ticket.gateways.TicketRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class RetrieveTicketUseCase {
  private final TicketRepository ticketRepository;
  public Mono<Ticket> retrieveTicketById(UUID id) {
    return ticketRepository.findById(id);
  }

  public Flux<Ticket> retrieveTicketsByTournamentIdAndCustomerId(UUID tournamentId, UUID customerId) {
    return ticketRepository.findAllByTournamentIdAndCustomerId(tournamentId, customerId);
  }
}

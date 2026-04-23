package com.fc2o.airtable.ticket;

import com.fc2o.airtable.ticket.mapper.TicketMapper;
import com.fc2o.model.ticket.Ticket;
import com.fc2o.model.ticket.gateways.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TicketHandler implements TicketRepository {

  private final TicketWebClient webClient;
  private final TicketMapper mapper;

  @Override
  public Mono<Ticket> findById(String id) {
    return webClient.retrieveById(id)
      .map(mapper::toTicket);
  }

  @Override
  public Flux<Ticket> findAllByTournamentIdAndCustomerId(String tournamentId, String customerId) {
    return webClient.retrieveAll()
      .flatMapMany(dto -> Flux.fromIterable(dto.records()))
      .filter(record -> record.fields().customerId().equals(customerId))
      .filter(record -> record.fields().tournamentId().equals(tournamentId))
      .map(mapper::toTicket);
  }

  @Override
  public Flux<Ticket> findAll() {
    return webClient.retrieveAll()
      .flatMapMany(dto -> Flux.fromIterable(dto.records()))
      .map(mapper::toTicket);
  }

  @Override
  public Mono<Ticket> save(Ticket ticket) {
    return webClient
      .create(mapper.toWrapperDto(ticket))
      .map(dto -> mapper.toTicket(dto.records().getFirst()));
  }

  @Override
  public Mono<Ticket> update(Ticket ticket) {
    return null;
  }

  @Override
  public Mono<Ticket> patch(Ticket ticket) {
    return webClient
      .patch(mapper.toWrapperDto(ticket))
      .map(dto -> mapper.toTicket(dto.records().getFirst()));
  }

  @Override
  public Mono<Ticket> deleteById(String id) {
    return null;
  }
}

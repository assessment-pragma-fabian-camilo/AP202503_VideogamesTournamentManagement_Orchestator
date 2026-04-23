package com.fc2o.airtable.transaction;

import com.fc2o.airtable.transaction.mapper.TransactionMapper;
import com.fc2o.model.transaction.gateways.TransactionRepository;
import com.fc2o.model.transaction.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TransactionHandler implements TransactionRepository {

  private final TransactionWebClient webClient;
  private final TransactionMapper mapper;

  @Override
  public Mono<Transaction> findById(String id) {
    return webClient.retrieveById(id)
      .map(mapper::toTransaction);
  }

  @Override
  public Mono<Transaction> findByTournamentId(String tournamentId) {
    return webClient.retrieveAll()
      .flatMapMany(dto -> Flux.fromIterable(dto.records()))
      .filter(record -> record.fields().tournamentId().equals(tournamentId))
      .singleOrEmpty()
      .map(mapper::toTransaction);
  }

  @Override
  public Mono<Transaction> findByTournamentIdAndCustomerId(String tournamentId, String customerId) {
    return webClient.retrieveAll()
      .flatMapMany(dto -> Flux.fromIterable(dto.records()))
      .filter(record -> record.fields().tournamentId().equals(tournamentId))
      .filter(record -> record.fields().tournamentId().equals(customerId))
      .singleOrEmpty()
      .map(mapper::toTransaction);
  }

  @Override
  public Mono<Transaction> retrieveByReference(String reference) {
    return webClient.retrieveAll()
      .flatMapMany(dto -> Flux.fromIterable(dto.records()))
      .filter(record -> record.fields().reference().equals(reference))
      .singleOrEmpty()
      .map(mapper::toTransaction);
  }

  @Override
  public Flux<Transaction> findAll() {
    return webClient.retrieveAll()
      .flatMapMany(dto -> Flux.fromIterable(dto.records()))
      .map(mapper::toTransaction);
  }

  @Override
  public Mono<Transaction> save(Transaction transaction) {
    return webClient
      .create(mapper.toWrapperDto(transaction))
      .map(dto -> mapper.toTransaction(dto.records().getFirst()));
  }

  @Override
  public Mono<Transaction> update(Transaction transaction) {
    return null;
  }

  @Override
  public Mono<Transaction> patch(Transaction transaction) {
    return webClient
      .patch(mapper.toWrapperDto(transaction))
      .map(dto -> mapper.toTransaction(dto.records().getFirst()));
  }

  @Override
  public Mono<Transaction> deleteById(String id) {
    return null;
  }
}

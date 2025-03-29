package com.fc2o.api.impl;

import com.fc2o.model.transaction.gateways.TransactionRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class Transaction implements TransactionRepository {
  @Override
  public Mono<com.fc2o.model.transaction.Transaction> findById(UUID id) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.transaction.Transaction> findByTournamentId(UUID tournamentId) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.transaction.Transaction> findByTournamentIdAndCustomerId(UUID tournamentId, UUID customerId) {
    return null;
  }

  @Override
  public Flux<com.fc2o.model.transaction.Transaction> findAll() {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.transaction.Transaction> save(com.fc2o.model.transaction.Transaction transaction) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.transaction.Transaction> update(com.fc2o.model.transaction.Transaction transaction) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.transaction.Transaction> patch(com.fc2o.model.transaction.Transaction transaction) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.transaction.Transaction> deleteById(UUID id) {
    return null;
  }
}

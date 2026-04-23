package com.fc2o.model.transaction.gateways;

import com.fc2o.model.transaction.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TransactionRepository {
  Mono<Transaction> findById(UUID id);
  Flux<Transaction> findAll();
  Mono<Transaction> save(Transaction transaction);
  Mono<Transaction> update(Transaction transaction);
  Mono<Transaction> patch(Transaction transaction);
  Mono<Transaction> deleteById(UUID id);
}

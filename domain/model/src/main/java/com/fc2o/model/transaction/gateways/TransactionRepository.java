package com.fc2o.model.transaction.gateways;

import com.fc2o.model.transaction.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface TransactionRepository {
  Mono<Transaction> findById(String id);
  Mono<Transaction> findByTournamentId(String tournamentId);
  Mono<Transaction> findByTournamentIdAndCustomerId(String tournamentId, String customerId);
  Mono<Transaction> retrieveByReference(String reference);
  Flux<Transaction> findAll();
  Mono<Transaction> save(Transaction transaction);
  Mono<Transaction> update(Transaction transaction);
  Mono<Transaction> patch(Transaction transaction);
  Mono<Transaction> deleteById(String id);
}

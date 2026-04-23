package com.fc2o.usecase.transaction.crud;

import com.fc2o.model.transaction.Transaction;
import com.fc2o.model.transaction.gateways.TransactionRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;



@RequiredArgsConstructor
public class RetrieveTransactionUseCase {
  private final TransactionRepository transactionRepository;

  public Mono<Transaction> retrieve(String transactionId) {
    return transactionRepository.findById(transactionId);
  }

  public Mono<Transaction> retrieveByTournamentIdAndCustomerId(String tournamentId, String customerId) {
    return transactionRepository.findByTournamentIdAndCustomerId(tournamentId, customerId);
  }
  public Mono<Transaction> retrieveByReference(String reference) {
    return transactionRepository.retrieveByReference(reference);
  }

}

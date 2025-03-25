package com.fc2o.usecase.transaction.crud;

import com.fc2o.model.transaction.Transaction;
import com.fc2o.model.transaction.gateways.TransactionRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class RetrieveTransactionUseCase {
  private final TransactionRepository transactionRepository;

  public Mono<Transaction> retrieve(UUID transactionId) {
    return transactionRepository.findById(transactionId);
  }

  public Mono<Transaction> retrieveByTournamentIdAndCustomerId(UUID tournamentId, UUID customerId) {
    return transactionRepository.findByTournamentIdAndCustomerId(tournamentId, customerId);
  }

}

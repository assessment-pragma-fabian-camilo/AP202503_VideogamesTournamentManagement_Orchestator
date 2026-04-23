package com.fc2o.usecase.transaction.crud;

import com.fc2o.model.transaction.Transaction;
import com.fc2o.model.transaction.gateways.TransactionRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateTransactionUseCase {
  private final TransactionRepository transactionRepository;

  public Mono<Transaction> create(Transaction transaction) {
    return transactionRepository.save(transaction);
  }
}

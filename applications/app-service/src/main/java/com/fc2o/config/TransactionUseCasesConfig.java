package com.fc2o.config;

import com.fc2o.model.transaction.gateways.TransactionRepository;
import com.fc2o.usecase.transaction.crud.CreateTransactionUseCase;
import com.fc2o.usecase.transaction.crud.RetrieveTransactionUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "com.fc2o.usecase.transaction",
  includeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")},
  useDefaultFilters = false
)
public class TransactionUseCasesConfig {

  @Bean
  public CreateTransactionUseCase createTransactionUseCase(TransactionRepository transactionRepository) {
    return new CreateTransactionUseCase(transactionRepository);
  }

  @Bean
  public RetrieveTransactionUseCase retrieveTransactionUseCase(TransactionRepository transactionRepository) {
    return new RetrieveTransactionUseCase(transactionRepository);
  }
}

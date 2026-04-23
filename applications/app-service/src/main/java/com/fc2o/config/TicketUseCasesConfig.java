package com.fc2o.config;

import com.fc2o.model.ticket.gateways.QrRepository;
import com.fc2o.model.ticket.gateways.TicketRepository;
import com.fc2o.service.ValidatePermissionsService;
import com.fc2o.service.ValidateTournamentPermissionsService;
import com.fc2o.usecase.ticket.business.BlockTicketUseCase;
import com.fc2o.usecase.ticket.crud.CreateQrUseCase;
import com.fc2o.usecase.ticket.business.RegisterTicketUseCase;
import com.fc2o.usecase.ticket.crud.CreateTicketUseCase;
import com.fc2o.usecase.ticket.crud.PatchTicketUseCase;
import com.fc2o.usecase.ticket.crud.RetrieveTicketUseCase;
import com.fc2o.usecase.transaction.crud.RetrieveTransactionUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "com.fc2o.usecase.ticket",
  includeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")},
  useDefaultFilters = false
)
public class TicketUseCasesConfig {

  @Bean
  public BlockTicketUseCase blockTicketUseCase(
    RetrieveTicketUseCase retrieveTicketUseCase,
    PatchTicketUseCase patchTicketUseCase,
    ValidatePermissionsService permissionsService,
    ValidateTournamentPermissionsService tournamentPermissionsService
  ) {
  return new BlockTicketUseCase(retrieveTicketUseCase, patchTicketUseCase, permissionsService, tournamentPermissionsService);
  }

  @Bean
  public RegisterTicketUseCase registerTicketUseCase(
    RetrieveTransactionUseCase retrieveTransactionUseCase,
    CreateTicketUseCase createTicketUseCase,
    CreateQrUseCase createQrUseCase,
    PatchTicketUseCase patchTicketUseCase
  ) {
    return new RegisterTicketUseCase(retrieveTransactionUseCase, createTicketUseCase, createQrUseCase, patchTicketUseCase);
  }

  @Bean
  public CreateTicketUseCase createTicketUseCase(TicketRepository ticketRepository) {
    return new CreateTicketUseCase(ticketRepository);
  }

  @Bean
  public PatchTicketUseCase patchTicketUseCase(TicketRepository ticketRepository) {
    return new PatchTicketUseCase(ticketRepository);
  }

  @Bean
  public RetrieveTicketUseCase retrieveTicketUseCase(TicketRepository ticketRepository) {
    return new RetrieveTicketUseCase(ticketRepository);
  }

  @Bean
  public CreateQrUseCase createQrUseCase(QrRepository qrRepository) {
    return new CreateQrUseCase(qrRepository);
  }
}

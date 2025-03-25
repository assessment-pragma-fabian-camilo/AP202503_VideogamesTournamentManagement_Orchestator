package com.fc2o.config;

import com.fc2o.model.tournament.gateways.TournamentRepository;
import com.fc2o.service.ValidatePermissionsService;
import com.fc2o.service.ValidateTournamentPermissionsService;
import com.fc2o.usecase.match.business.CancelMatchUseCase;
import com.fc2o.usecase.match.crud.RetrieveMatchUseCase;
import com.fc2o.usecase.registration.crud.CreateRegistrationUseCase;
import com.fc2o.usecase.registration.crud.PatchRegistrationUseCase;
import com.fc2o.usecase.reward.business.RegisterRewardUseCase;
import com.fc2o.usecase.ticket.business.BlockTicketUseCase;
import com.fc2o.usecase.ticket.crud.PatchTicketUseCase;
import com.fc2o.usecase.ticket.crud.RetrieveTicketUseCase;
import com.fc2o.usecase.tournament.business.*;
import com.fc2o.usecase.tournament.crud.CreateTournamentUseCase;
import com.fc2o.usecase.tournament.crud.PatchTournamentUseCase;
import com.fc2o.usecase.tournament.crud.RetrieveTournamentUseCase;
import com.fc2o.usecase.transaction.crud.CreateTransactionUseCase;
import com.fc2o.usecase.user.crud.RetrieveUserUseCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "com.fc2o.usecase.tournament",
  includeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")},
  useDefaultFilters = false
)
public class TournamentUseCasesConfig {

  @Bean
  public AddModTournamentUseCase addModTournamentUseCase(
    PatchTournamentUseCase patchTournamentUseCase,
    RetrieveTournamentUseCase retrieveTournamentUseCase,
    ValidateTournamentPermissionsService validateTournamentPermissionsService,
    @Value("${tournament.max-mods}") Integer maxMods
  ) {
    return new AddModTournamentUseCase(
      patchTournamentUseCase,
      retrieveTournamentUseCase,
      validateTournamentPermissionsService,
      maxMods
    );
  }

  @Bean
  public CancelTournamentUseCase cancelTournamentUseCase(
    RetrieveTournamentUseCase retrieveTournamentUseCase,
    PatchTournamentUseCase patchTournamentUseCase,
    ValidateTournamentPermissionsService permissionsService,
    CancelMatchUseCase cancelMatchUseCase
  ) {
    return new CancelTournamentUseCase(
      retrieveTournamentUseCase,
      patchTournamentUseCase,
      permissionsService,
      cancelMatchUseCase
    );
  }

  @Bean
  public DisqualifyParticipantUseCase disqualifyParticipantUseCase(
    RetrieveTournamentUseCase retrieveTournamentUseCase,
    PatchTournamentUseCase patchTournamentUseCase,
    ValidateTournamentPermissionsService permissionsService,
    CancelMatchUseCase cancelMatchUseCase,
    BlockTicketUseCase blockTicketUseCase
  ) {
    return new DisqualifyParticipantUseCase(
      retrieveTournamentUseCase,
      patchTournamentUseCase,
      permissionsService,
      cancelMatchUseCase,
      blockTicketUseCase
    );
  }

  @Bean
  public FinalizeTournamentUseCase finalizeTournamentUseCase(
    RetrieveTournamentUseCase retrieveTournamentUseCase,
    PatchTournamentUseCase patchTournamentUseCase,
    ValidateTournamentPermissionsService permissionsService,
    RetrieveMatchUseCase retrieveMatchUseCase,
    CancelMatchUseCase cancelMatchUseCase,
    RegisterRewardUseCase registerRewardUseCase
  ) {
    return new FinalizeTournamentUseCase(
      retrieveTournamentUseCase,
      patchTournamentUseCase,
      permissionsService,
      retrieveMatchUseCase,
      cancelMatchUseCase,
      registerRewardUseCase
    );
  }

  @Bean
  public PreRegisterParticipantUseCase preRegisterParticipantUseCase(
    RetrieveTournamentUseCase retrieveTournamentUseCase,
    PatchTournamentUseCase patchTournamentUseCase,
    CreateRegistrationUseCase createRegistrationUseCase,
    CreateTransactionUseCase createTransactionUseCase,
    ValidateTournamentPermissionsService permissionsService,
    RetrieveUserUseCase retrieveUserUseCase
  ) {
    return new PreRegisterParticipantUseCase(
      retrieveTournamentUseCase,
      patchTournamentUseCase,
      createRegistrationUseCase,
      createTransactionUseCase,
      permissionsService,
      retrieveUserUseCase
    );
  }

  @Bean
  public RegisterParticipantUseCase registerParticipantUseCase(
    RetrieveTicketUseCase retrieveTicketUseCase,
    PatchTournamentUseCase patchTournamentUseCase,
    RetrieveTournamentUseCase retrieveTournamentUseCase,
    PatchTicketUseCase patchTicketUseCase,
    PatchRegistrationUseCase patchRegistrationUseCase,
    ValidateTournamentPermissionsService permissionsService,
    RetrieveUserUseCase retrieveUserUseCase
  ) {
    return new RegisterParticipantUseCase(
      retrieveTicketUseCase,
      patchTournamentUseCase,
      retrieveTournamentUseCase,
      patchTicketUseCase,
      patchRegistrationUseCase,
      permissionsService,
      retrieveUserUseCase
    );
  }

  @Bean
  public RegisterTournamentUseCase registerTournamentUseCase(
    RetrieveUserUseCase retrieveUserUseCase,
    RetrieveTournamentUseCase retrieveTournamentUseCase,
    CreateTournamentUseCase createTournamentUseCase,
    ValidatePermissionsService permissionsService,
    @Value("${tournament.max-free-tournaments}") Short maxFreeTournaments
  ) {
    return new RegisterTournamentUseCase(
      retrieveUserUseCase,
      retrieveTournamentUseCase,
      createTournamentUseCase,
      permissionsService,
      maxFreeTournaments
    );
  }

  @Bean
  public RescheduleTournamentUseCase rescheduleTournamentUseCase(
    RetrieveTournamentUseCase retrieveTournamentUseCase,
    PatchTournamentUseCase patchTournamentUseCase,
    ValidateTournamentPermissionsService permissionsService
  ) {
    return new RescheduleTournamentUseCase(retrieveTournamentUseCase, patchTournamentUseCase, permissionsService);
  }

  @Bean
  public StartTournamentUseCase startTournamentUseCase(
    RetrieveTournamentUseCase retrieveTournamentUseCase,
    PatchTournamentUseCase patchTournamentUseCase,
    ValidateTournamentPermissionsService permissionsService
  ) {
    return new StartTournamentUseCase(retrieveTournamentUseCase, patchTournamentUseCase, permissionsService);
  }

  @Bean
  public CreateTournamentUseCase createTournamentUseCase(TournamentRepository tournamentRepository) {
    return new CreateTournamentUseCase(tournamentRepository);
  }

  @Bean
  public PatchTournamentUseCase patchTournamentUseCase(TournamentRepository tournamentRepository) {
    return new PatchTournamentUseCase(tournamentRepository);
  }

  @Bean
  public RetrieveTournamentUseCase retrieveTournamentUseCase(TournamentRepository tournamentRepository) {
    return new RetrieveTournamentUseCase(tournamentRepository);
  }
}

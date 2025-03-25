package com.fc2o.config;

import com.fc2o.model.match.gateways.MatchRepository;
import com.fc2o.service.ValidateTournamentPermissionsService;
import com.fc2o.usecase.match.business.CancelMatchUseCase;
import com.fc2o.usecase.match.business.FinalizeMatchUseCase;
import com.fc2o.usecase.match.business.RegisterMatchUseCase;
import com.fc2o.usecase.match.business.StartMatchUseCase;
import com.fc2o.usecase.match.crud.CreateMatchUseCase;
import com.fc2o.usecase.match.crud.PatchMatchUseCase;
import com.fc2o.usecase.match.crud.RetrieveMatchUseCase;
import com.fc2o.usecase.tournament.crud.RetrieveTournamentUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "com.fc2o.usecase.match",
  includeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")},
  useDefaultFilters = false
)
public class MatchUseCasesConfig {

  @Bean
  public CancelMatchUseCase cancelMatchUseCase(
    RetrieveMatchUseCase retrieveMatchUseCase,
    PatchMatchUseCase patchMatchUseCase,
    ValidateTournamentPermissionsService permissionsService
  ) {
  return new CancelMatchUseCase(retrieveMatchUseCase, patchMatchUseCase, permissionsService);
  }

  @Bean
  public FinalizeMatchUseCase finalizeMatchUseCase(
    RetrieveMatchUseCase retrieveMatchUseCase,
    PatchMatchUseCase patchMatchUseCase,
    ValidateTournamentPermissionsService permissionsService
  ) {
    return new FinalizeMatchUseCase(retrieveMatchUseCase, patchMatchUseCase, permissionsService);
  }

  @Bean
  public RegisterMatchUseCase registerMatchUseCase(
    RetrieveTournamentUseCase retrieveTournamentUseCase,
    CreateMatchUseCase createMatchUseCase,
    ValidateTournamentPermissionsService permissionsService
  ) {
    return new RegisterMatchUseCase(retrieveTournamentUseCase, createMatchUseCase, permissionsService);
  }

  @Bean
  public StartMatchUseCase startMatchUseCase(
    RetrieveMatchUseCase retrieveMatchUseCase,
  PatchMatchUseCase patchMatchUseCase,
  ValidateTournamentPermissionsService permissionsService
  ) {
  return new StartMatchUseCase(retrieveMatchUseCase, patchMatchUseCase, permissionsService);
  }

  @Bean
  public CreateMatchUseCase createMatchUseCase(MatchRepository matchRepository) {
    return new CreateMatchUseCase(matchRepository);
  }

  @Bean
  public PatchMatchUseCase patchMatchUseCase(MatchRepository matchRepository) {
    return new PatchMatchUseCase(matchRepository);
  }

  @Bean
  public RetrieveMatchUseCase retrieveMatchUseCase(MatchRepository matchRepository) {
    return new RetrieveMatchUseCase(matchRepository);
  }
}

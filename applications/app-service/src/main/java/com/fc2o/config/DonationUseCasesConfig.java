package com.fc2o.config;

import com.fc2o.model.donation.gateways.DonationRepository;
import com.fc2o.usecase.donation.business.RegisterDonationUseCase;
import com.fc2o.usecase.donation.crud.CreateDonationUseCase;
import com.fc2o.usecase.tournament.crud.RetrieveTournamentUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "com.fc2o.usecase.donation",
  includeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")},
  useDefaultFilters = false
)
public class DonationUseCasesConfig {

  @Bean
  public RegisterDonationUseCase registerDonationUseCase(
    CreateDonationUseCase createDonationUseCase,
    RetrieveTournamentUseCase retrieveTournamentUseCase
  ) {
    return new RegisterDonationUseCase(createDonationUseCase, retrieveTournamentUseCase);
  }

  @Bean
  public CreateDonationUseCase createDonationUseCase(DonationRepository donationRepository) {
    return new CreateDonationUseCase(donationRepository);
  }
}

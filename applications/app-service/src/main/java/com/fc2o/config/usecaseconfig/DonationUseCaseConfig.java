package com.fc2o.config.usecaseconfig;

import com.fc2o.model.donation.gateways.DonationRepository;
import com.fc2o.model.commission.gateways.CommissionRepository;
import com.fc2o.model.tournament.gateways.TournamentRepository;
import com.fc2o.model.user.gateways.UserRepository;
import com.fc2o.usecase.donation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Donation use cases.
 */
@Configuration
public class DonationUseCaseConfig {

    @Bean
    public CreateDonationUseCase createDonationUseCase(
            DonationRepository donationRepository,
            TournamentRepository tournamentRepository,
            UserRepository userRepository) {
        return new CreateDonationUseCase(
            donationRepository,
            tournamentRepository,
            userRepository
        );
    }

    @Bean
    public GetDonationUseCase getDonationUseCase(
            DonationRepository donationRepository) {
        return new GetDonationUseCase(donationRepository);
    }

    @Bean
    public ListDonationsUseCase listDonationsUseCase(
            DonationRepository donationRepository) {
        return new ListDonationsUseCase(donationRepository);
    }

    @Bean
    public UpdateDonationStatusUseCase updateDonationStatusUseCase(
            DonationRepository donationRepository) {
        return new UpdateDonationStatusUseCase(donationRepository);
    }

    @Bean
    public CalculateDonationCommissionUseCase calculateDonationCommissionUseCase(
            DonationRepository donationRepository,
            TournamentRepository tournamentRepository,
            CommissionRepository commissionRepository) {
        return new CalculateDonationCommissionUseCase(
            donationRepository,
            tournamentRepository,
            commissionRepository
        );
    }
}


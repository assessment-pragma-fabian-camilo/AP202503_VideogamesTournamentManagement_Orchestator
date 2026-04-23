package com.fc2o.usecase.donation;

import com.fc2o.model.donation.Donation;
import com.fc2o.model.donation.gateways.DonationRepository;
import reactor.core.publisher.Flux;

/**
 * Use case for listing donations by tournament.
 */
public class ListDonationsUseCase {
    private final DonationRepository donationRepository;

    public ListDonationsUseCase(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    /**
     * List all donations for a specific tournament.
     */
    public Flux<Donation> executeByTournament(String tournamentId) {
        if (tournamentId == null || tournamentId.isBlank()) {
            return Flux.error(new IllegalArgumentException("Tournament ID is required"));
        }
        return donationRepository.findAllByTournamentId(tournamentId);
    }
}


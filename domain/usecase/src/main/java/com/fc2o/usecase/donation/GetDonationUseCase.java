package com.fc2o.usecase.donation;

import com.fc2o.model.donation.Donation;
import com.fc2o.model.donation.gateways.DonationRepository;
import reactor.core.publisher.Mono;

/**
 * Use case for retrieving a donation by ID.
 */
public class GetDonationUseCase {
    private final DonationRepository donationRepository;

    public GetDonationUseCase(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public Mono<Donation> execute(String donationId) {
        if (donationId == null || donationId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Donation ID is required"));
        }
        return donationRepository.findById(donationId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Donation not found with ID: " + donationId)));
    }
}


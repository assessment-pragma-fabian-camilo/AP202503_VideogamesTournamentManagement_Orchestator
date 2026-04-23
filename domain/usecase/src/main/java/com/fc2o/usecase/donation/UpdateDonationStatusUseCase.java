package com.fc2o.usecase.donation;

import com.fc2o.model.donation.Donation;
import com.fc2o.model.donation.gateways.DonationRepository;
import com.fc2o.model.shared.TransactionStatus;
import reactor.core.publisher.Mono;

/**
 * Use case for updating donation status.
 */
public class UpdateDonationStatusUseCase {
    private final DonationRepository donationRepository;

    public UpdateDonationStatusUseCase(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public Mono<Donation> execute(String donationId, TransactionStatus newStatus) {
        if (donationId == null || donationId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Donation ID is required"));
        }
        if (newStatus == null) {
            return Mono.error(new IllegalArgumentException("New status is required"));
        }

        return donationRepository.findById(donationId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Donation not found")))
                .flatMap(donation -> {
                    Donation updatedDonation = donation.toBuilder()
                            .status(newStatus)
                            .build();
                    return donationRepository.update(updatedDonation);
                });
    }
}


package com.fc2o.usecase.donation;

import com.fc2o.model.commission.gateways.CommissionRepository;
import com.fc2o.model.donation.gateways.DonationRepository;
import com.fc2o.model.tournament.gateways.TournamentRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Use case for calculating commission on a donation.
 * Commission is calculated based on tournament type (FREE/PAID) and donation amount.
 */
@RequiredArgsConstructor
//TODO
public class CalculateDonationCommissionUseCase {
    private final DonationRepository donationRepository;
    private final TournamentRepository tournamentRepository;
    private final CommissionRepository commissionRepository;

    public Mono<Double> execute(String donationId) {
        if (donationId == null || donationId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Donation ID is required"));
        }

        return donationRepository.findById(donationId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Donation not found")))
                .flatMap(donation -> {
                    return tournamentRepository.findById(donation.tournamentId())
                            .switchIfEmpty(Mono.error(new IllegalArgumentException("Tournament not found")))
                            .flatMap(tournament -> {
                                var commissionType = tournament.isPaid() 
                                    ? com.fc2o.model.shared.CommissionType.PAID
                                    : com.fc2o.model.shared.CommissionType.FREE;
                                
                                return commissionRepository.findByCommissionType(commissionType)
                                        .switchIfEmpty(Mono.error(new IllegalArgumentException("Commission not found")))
                                        .map(commission -> {
                                            // Calculate: donation amount * donation percentage
                                            double percentage = commission.donationPercentage();
                                            return donation.amount().doubleValue() * (percentage / 100.0);
                                        });
                            });
                });
    }
}


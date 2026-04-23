package com.fc2o.usecase.donation;

import com.fc2o.model.donation.Donation;
import com.fc2o.model.donation.gateways.DonationRepository;
import com.fc2o.model.shared.TransactionStatus;
import com.fc2o.model.tournament.gateways.TournamentRepository;
import com.fc2o.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Use case for creating a donation.
 * Business rules:
 * - Any user can make donations
 * - Only to tournaments in ONGOING status
 * - Can be to a specific team or to the tournament in general (team_id nullable)
 * - Initial status is STARTED
 */
@RequiredArgsConstructor
public class CreateDonationUseCase {
    private final DonationRepository donationRepository;
    private final TournamentRepository tournamentRepository;
    private final UserRepository userRepository;

    public Mono<Donation> execute(Donation donation) {

        if (donation.tournamentId() == null || donation.tournamentId().isBlank()) {
            return Mono.error(new IllegalArgumentException("Tournament ID is required"));
        }
        if (donation.userId() == null || donation.userId().isBlank()) {
            return Mono.error(new IllegalArgumentException("User ID is required"));
        }
        if (donation.amount() == null || donation.amount() <= 0) {
            return Mono.error(new IllegalArgumentException("Amount must be greater than 0"));
        }

        return userRepository.isActiveUser(donation.userId())
                .switchIfEmpty(Mono.error(new IllegalArgumentException("User not found")))
                .flatMap(user -> tournamentRepository.findById(donation.tournamentId())
                        .switchIfEmpty(Mono.error(new IllegalArgumentException("Tournament not found")))
                        .flatMap(tournament -> {
                            if (!tournament.isOngoing()) {
                                return Mono.error(new IllegalArgumentException(
                                        "Can only donate to ONGOING tournaments"));
                            }

                            donation.toBuilder().status(TransactionStatus.STARTED);
                            return donationRepository.save(donation);
                        }));
    }
}


package com.fc2o.usecase.donation.crud;

import com.fc2o.model.donation.Donation;
import com.fc2o.model.donation.gateways.DonationRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateDonationUseCase {
  private final DonationRepository donationRepository;

  public Mono<Donation> create(Donation donation) {
    return donationRepository.save(donation);
  }
}

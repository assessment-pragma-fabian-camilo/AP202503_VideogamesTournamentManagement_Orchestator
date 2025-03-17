package com.fc2o.model.donation.gateways;

import com.fc2o.model.donation.Donation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface DonationRepository {
  Mono<Donation> findById(UUID id);
  Flux<Donation> findAll();
  Mono<Donation> save(Donation donation);
  Mono<Donation> update(Donation donation);
  Mono<Donation> patch(Donation donation);
  Mono<Donation> deleteById(UUID id);
}

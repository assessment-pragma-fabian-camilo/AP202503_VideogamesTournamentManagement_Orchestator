package com.fc2o.api.impl;

import com.fc2o.model.donation.gateways.DonationRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class Donation implements DonationRepository {
  @Override
  public Mono<com.fc2o.model.donation.Donation> findById(UUID id) {
    return null;
  }

  @Override
  public Flux<com.fc2o.model.donation.Donation> findAll() {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.donation.Donation> save(com.fc2o.model.donation.Donation donation) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.donation.Donation> update(com.fc2o.model.donation.Donation donation) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.donation.Donation> patch(com.fc2o.model.donation.Donation donation) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.donation.Donation> deleteById(UUID id) {
    return null;
  }
}

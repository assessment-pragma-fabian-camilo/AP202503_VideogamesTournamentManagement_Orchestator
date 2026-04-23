package com.fc2o.airtable.donation;

import com.fc2o.airtable.donation.mapper.DonationMapper;
import com.fc2o.model.donation.Donation;
import com.fc2o.model.donation.gateways.DonationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class DonationHandler implements DonationRepository {

  private final DonationWebClient webClient;
  private final DonationMapper mapper;

  @Override
  public Mono<Donation> findById(String id) {
    return webClient.retrieveById(id)
      .map(mapper::toDonation);
  }

  @Override
  public Flux<Donation> findAll() {
    return webClient.retrieveAll()
      .flatMapMany(dto -> Flux.fromIterable(dto.records()))
      .map(mapper::toDonation);
  }

  @Override
  public Mono<Donation> save(Donation donation) {
    return webClient
      .create(mapper.toWrapperDto(donation))
      .map(dto -> mapper.toDonation(dto.records().getFirst()));
  }

  @Override
  public Mono<Donation> update(Donation donation) {
    return null;
  }

  @Override
  public Mono<Donation> patch(Donation donation) {
    return webClient
      .patch(mapper.toWrapperDto(donation))
      .map(dto -> mapper.toDonation(dto.records().getFirst()));
  }

  @Override
  public Mono<Donation> deleteById(String id) {
    return null;
  }
}

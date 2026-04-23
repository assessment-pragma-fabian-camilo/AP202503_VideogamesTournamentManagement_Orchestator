package com.fc2o.airtable.registration;

import com.fc2o.airtable.registration.mapper.RegistrationMapper;
import com.fc2o.model.registration.Registration;
import com.fc2o.model.registration.gateways.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RegistrationHandler implements RegistrationRepository {

  private final RegistrationWebClient webClient;
  private final RegistrationMapper mapper;

  @Override
  public Mono<Registration> findById(String id) {
    return webClient.retrieveById(id)
      .map(mapper::toRegistration);
  }

  @Override
  public Mono<Registration> patchByTournamentIdAndParticipantId(String tournamentId, String participantId, Registration registration) {
    return webClient
      .retrieveAll()
      .flatMapMany(wrapper -> Flux.fromIterable(wrapper.records()))
      .filter(record -> record.fields().tournamentId().equals(tournamentId))
      .filter(record -> record.fields().participantId().equals(participantId))
      .singleOrEmpty()
      .map(record -> registration.toBuilder().id(record.id()).build())
      .map(mapper::toWrapperDto)
      .flatMap(webClient::patch)
      .map(dto -> mapper.toRegistration(dto.records().getFirst()));
  }

  @Override
  public Flux<Registration> findAll() {
    return webClient.retrieveAll()
      .flatMapMany(dto -> Flux.fromIterable(dto.records()))
      .map(mapper::toRegistration);
  }

  @Override
  public Mono<Registration> save(Registration registration) {
    return webClient
      .create(mapper.toWrapperDto(registration))
      .map(dto -> mapper.toRegistration(dto.records().getFirst()));
  }

  @Override
  public Mono<Registration> update(Registration registration) {
    return null;
  }

  @Override
  public Mono<Registration> patch(Registration registration) {
    return webClient
      .patch(mapper.toWrapperDto(registration))
      .map(dto -> mapper.toRegistration(dto.records().getFirst()));
  }

  @Override
  public Mono<Registration> deleteById(String id) {
    return null;
  }
}

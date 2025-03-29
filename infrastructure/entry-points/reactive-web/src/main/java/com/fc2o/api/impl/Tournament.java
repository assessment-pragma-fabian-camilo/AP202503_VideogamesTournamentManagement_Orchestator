package com.fc2o.api.impl;

import com.fc2o.model.tournament.gateways.TournamentRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Component
public class Tournament implements TournamentRepository {
  @Override
  public Mono<com.fc2o.model.tournament.Tournament> findById(UUID id) {
    return null;
  }

  @Override
  public Flux<com.fc2o.model.tournament.Tournament> findAll() {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.tournament.Tournament> save(com.fc2o.model.tournament.Tournament tournament) {
    return Mono.just(com.fc2o.model.tournament.Tournament.builder().id(UUID.randomUUID()).build());
  }

  @Override
  public Mono<com.fc2o.model.tournament.Tournament> update(com.fc2o.model.tournament.Tournament tournament) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.tournament.Tournament> patch(com.fc2o.model.tournament.Tournament tournament) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.tournament.Tournament> deleteById(UUID id) {
    return null;
  }

  @Override
  public Flux<com.fc2o.model.tournament.Tournament> findAllByPromoterId(UUID promoterId) {
    return Flux.fromIterable(
      List.of(
        com.fc2o.model.tournament.Tournament.builder().build(),
        com.fc2o.model.tournament.Tournament.builder().build(),
        com.fc2o.model.tournament.Tournament.builder().build()
      )
    );
  }

  @Override
  public Mono<com.fc2o.model.tournament.Tournament> patchRegisterParticipant(UUID participantId) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.tournament.Tournament> patchDisqualify(UUID participantId) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.tournament.Tournament> patchPreRegisterParticipant(UUID participantId) {
    return null;
  }
}

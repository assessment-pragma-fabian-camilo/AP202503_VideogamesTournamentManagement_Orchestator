package com.fc2o.model.tournament.gateways;

import com.fc2o.model.tournament.Tournament;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TournamentRepository {
  Mono<Tournament> findById(UUID id);
  Flux<Tournament> findAll();
  Mono<Tournament> save(Tournament tournament);
  Mono<Tournament> update(Tournament tournament);
  Mono<Tournament> patch(Tournament tournament);
  Mono<Tournament> deleteById(UUID id);
  Flux<Tournament> findAllByPromoterId(UUID promoterId);
  Mono<Tournament> patchRegisterParticipant(UUID participantId);
  Mono<Tournament> patchDisqualify(UUID participantId);
  Mono<Tournament> patchPreRegisterParticipant(UUID participantId);
}

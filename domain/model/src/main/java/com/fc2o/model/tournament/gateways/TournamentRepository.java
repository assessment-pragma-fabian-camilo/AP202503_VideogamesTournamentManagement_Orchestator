package com.fc2o.model.tournament.gateways;

import com.fc2o.model.tournament.Tournament;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TournamentRepository {
  Mono<Tournament> findById(String id);
  Flux<Tournament> findAll();
  Mono<Tournament> save(Tournament tournament);
  Mono<Tournament> update(Tournament tournament);
  Mono<Tournament> patch(Tournament tournament);
  Mono<Tournament> deleteById(String id);
  Flux<Tournament> findAllByPromoterId(String promoterId);
  Mono<Tournament> patchRegisterParticipant(String tournamentId, String participantId);
  Mono<Tournament> patchDisqualify(String tournamentId, String participantId);
  Mono<Tournament> patchPreRegisterParticipant(String tournamentId, String participantId);
}

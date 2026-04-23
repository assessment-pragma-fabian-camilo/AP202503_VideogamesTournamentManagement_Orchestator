package com.fc2o.model.match.gateways;

import com.fc2o.model.match.Match;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface MatchRepository {
  Mono<Match> findById(UUID id);
  Flux<Match> findAll();
  Flux<Match> findAllByTournamentId(UUID tournamentId);
  Flux<Match> findAllByParticipantIdAndTournamentId(UUID participantId, UUID tournamentId);
  Mono<Match> save(Match match);
  Mono<Match> update(Match match);
  Mono<Match> patch(Match match);
  Mono<Match> deleteById(UUID id);

}

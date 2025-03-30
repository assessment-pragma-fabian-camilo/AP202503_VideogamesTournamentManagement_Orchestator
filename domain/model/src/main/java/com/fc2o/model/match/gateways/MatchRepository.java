package com.fc2o.model.match.gateways;

import com.fc2o.model.match.Match;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface MatchRepository {
  Mono<Match> findById(String id);
  Flux<Match> findAll();
  Flux<Match> findAllByTournamentId(String tournamentId);
  Flux<Match> findAllByParticipantIdAndTournamentId(String participantId, String tournamentId);
  Mono<Match> save(Match match);
  Mono<Match> update(Match match);
  Mono<Match> patch(Match match);
  Mono<Match> deleteById(String id);

}

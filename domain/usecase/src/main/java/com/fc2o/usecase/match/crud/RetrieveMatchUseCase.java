package com.fc2o.usecase.match.crud;

import com.fc2o.model.match.Match;
import com.fc2o.model.match.gateways.MatchRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RetrieveMatchUseCase {
  private final MatchRepository matchRepository;

  public Mono<Match> retrieve(String matchId) {
    return matchRepository.findById(matchId);
  }

  public Flux<Match> retrieveAllByParticipantIdAndTournamentId(String participantId, String tournamentId) {
    return matchRepository.findAllByParticipantIdAndTournamentId(participantId, tournamentId);
  }

  public Flux<Match> retrieveAllByTournamentId(String tournamentId) {
    return matchRepository.findAllByTournamentId(tournamentId);
  }
}

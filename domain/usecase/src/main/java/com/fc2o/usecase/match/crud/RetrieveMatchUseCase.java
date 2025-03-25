package com.fc2o.usecase.match.crud;

import com.fc2o.model.match.Match;
import com.fc2o.model.match.gateways.MatchRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class RetrieveMatchUseCase {
  private final MatchRepository matchRepository;

  public Mono<Match> retrieve(UUID matchId) {
    return matchRepository.findById(matchId);
  }

  public Flux<Match> retrieveAllByParticipantIdAndTournamentId(UUID participantId, UUID tournamentId) {
    return matchRepository.findAllByParticipantIdAndTournamentId(participantId, tournamentId);
  }

  public Flux<Match> retrieveAllByTournamentId(UUID tournamentId) {
    return matchRepository.findAllByTournamentId(tournamentId);
  }
}

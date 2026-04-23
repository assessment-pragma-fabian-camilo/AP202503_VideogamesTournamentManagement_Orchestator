package com.fc2o.usecase.match;

import com.fc2o.model.match.Match;
import com.fc2o.model.match.gateways.MatchRepository;
import reactor.core.publisher.Mono;

/**
 * Use case for retrieving a match by ID.
 */
public class GetMatchUseCase {
    private final MatchRepository matchRepository;

    public GetMatchUseCase(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public Mono<Match> execute(String matchId) {
        if (matchId == null || matchId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Match ID is required"));
        }
        return matchRepository.findById(matchId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Match not found with ID: " + matchId)));
    }
}


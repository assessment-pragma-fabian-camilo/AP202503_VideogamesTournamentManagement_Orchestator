package com.fc2o.usecase.match;

import com.fc2o.model.match.Match;
import com.fc2o.model.match.gateways.MatchRepository;
import reactor.core.publisher.Mono;

/**
 * Use case for updating a match (basic info).
 */
public class UpdateMatchUseCase {
    private final MatchRepository matchRepository;

    public UpdateMatchUseCase(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public Mono<Match> execute(String matchId, String dateTimeStart) {
        if (matchId == null || matchId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Match ID is required"));
        }

        return matchRepository.findById(matchId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Match not found")))
                .flatMap(match -> {
                    Match updatedMatch = match.toBuilder()
                            .startDateTime(dateTimeStart != null ? dateTimeStart : match.startDateTime())
                            .build();
                    return matchRepository.update(updatedMatch);
                });
    }
}


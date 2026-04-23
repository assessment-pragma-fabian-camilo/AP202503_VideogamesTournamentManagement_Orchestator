package com.fc2o.usecase.match;

import com.fc2o.model.match.Match;
import com.fc2o.model.match.gateways.MatchRepository;
import reactor.core.publisher.Mono;

/**
 * Use case for updating match details (match_details field).
 */
public class UpdateMatchDetailsUseCase {
    private final MatchRepository matchRepository;

    public UpdateMatchDetailsUseCase(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public Mono<Match> execute(String matchId, String matchDetails) {
        if (matchId == null || matchId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Match ID is required"));
        }

        return matchRepository.findById(matchId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Match not found")))
                .flatMap(match -> {
                    // Can update details even if completed, but cannot change winner/status
                    Match updatedMatch = match.toBuilder()
                            .build();  // Placeholder - would need match_details field
                    return matchRepository.update(updatedMatch);
                });
    }
}


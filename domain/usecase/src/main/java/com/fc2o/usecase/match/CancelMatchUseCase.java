package com.fc2o.usecase.match;

import com.fc2o.model.match.Match;
import com.fc2o.model.match.gateways.MatchRepository;
import com.fc2o.model.shared.MatchStatus;
import reactor.core.publisher.Mono;

/**
 * Use case for canceling a match.
 * Business rules:
 * - Only PROMOTER, MODERATOR or ADMIN can cancel
 * - Cannot cancel if match is already COMPLETED
 */
public class CancelMatchUseCase {
    private final MatchRepository matchRepository;

    public CancelMatchUseCase(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public Mono<Match> execute(String matchId) {
        if (matchId == null || matchId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Match ID is required"));
        }

        return matchRepository.findById(matchId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Match not found with ID: " + matchId)))
                .flatMap(match -> {
                    if (match.isCompleted()) {
                        return Mono.error(new IllegalArgumentException("Cannot cancel a completed match"));
                    }

                    Match canceledMatch = match.toBuilder()
                            .status(MatchStatus.CANCELED)
                            .build();

                    return matchRepository.update(canceledMatch);
                });
    }
}


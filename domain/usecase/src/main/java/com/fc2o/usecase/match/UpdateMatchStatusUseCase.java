package com.fc2o.usecase.match;

import com.fc2o.model.match.Match;
import com.fc2o.model.match.gateways.MatchRepository;
import com.fc2o.model.shared.MatchStatus;
import reactor.core.publisher.Mono;

/**
 * Use case for updating match status.
 * Business rules:
 * - Only PROMOTER, MODERATOR or ADMIN can update status
 * - Valid transitions: SCHEDULED → ONGOING → COMPLETED/CANCELED
 * - Winner can only be defined when match is ONGOING
 * - Once COMPLETED, no status changes allowed
 */
public class UpdateMatchStatusUseCase {
    private final MatchRepository matchRepository;

    public UpdateMatchStatusUseCase(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public Mono<Match> execute(String matchId, MatchStatus newStatus) {
        if (matchId == null || matchId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Match ID is required"));
        }
        if (newStatus == null) {
            return Mono.error(new IllegalArgumentException("New status is required"));
        }

        return matchRepository.findById(matchId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Match not found with ID: " + matchId)))
                .flatMap(currentMatch -> {
                    MatchStatus currentStatus = currentMatch.status();

                    // Validate state transition
                    if (!isValidTransition(currentStatus, newStatus)) {
                        return Mono.error(new IllegalArgumentException(
                                "Invalid status transition from " + currentStatus + " to " + newStatus));
                    }

                    if (currentMatch.isCompleted()) {
                        return Mono.error(new IllegalArgumentException(
                                "Cannot modify a completed match"));
                    }

                    Match updatedMatch = currentMatch.toBuilder()
                            .status(newStatus)
                            .build();

                    return matchRepository.update(updatedMatch);
                });
    }

    private boolean isValidTransition(MatchStatus from, MatchStatus to) {

        return (from == MatchStatus.SCHEDULED && (to == MatchStatus.ONGOING || to == MatchStatus.CANCELED)) ||
               (from == MatchStatus.ONGOING && (to == MatchStatus.COMPLETED || to == MatchStatus.CANCELED)) ||
               (from == MatchStatus.SCHEDULED && to == MatchStatus.SCHEDULED) ||
               (from == MatchStatus.ONGOING && to == MatchStatus.ONGOING) ||
               (from == MatchStatus.COMPLETED && to == MatchStatus.COMPLETED) ||
               (from == MatchStatus.CANCELED && to == MatchStatus.CANCELED);
    }
}


package com.fc2o.usecase.match;

import com.fc2o.model.match.Match;
import com.fc2o.model.match.gateways.MatchRepository;
import com.fc2o.model.shared.MatchStatus;
import com.fc2o.usecase.tournamentteam.CheckTeamInTournamentUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Use case for defining the winner of a match.
 * Business rules:
 * - Only PROMOTER, MODERATOR or ADMIN can define winners
 * - Match must be in ONGOING status
 * - Winner team must be one of the match participants
 */
@RequiredArgsConstructor
public class DefineMatchWinnerUseCase {
    private final MatchRepository matchRepository;
    private final CheckTeamInTournamentUseCase checkTeamInTournamentUseCase;

    public Mono<Match> execute(String matchId, String winnerTeamId) {
        if (matchId == null || matchId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Match ID is required"));
        }
        if (winnerTeamId == null || winnerTeamId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Winner team ID is required"));
        }

        return matchRepository.findById(matchId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Match not found with ID: " + matchId)))
                .flatMap(match -> {
                    // Match must be ONGOING to define winner
                    if (!match.isOngoing()) {
                        return Mono.error(new IllegalArgumentException(
                                "Can only define winner for ONGOING matches"));
                    }

                    // Winner must be a participant
                    if (!match.participantIds().contains(winnerTeamId)) {
                        return Mono.error(new IllegalArgumentException(
                                "Winner team must be a participant in this match"));
                    }

                    Match updatedMatch = match.toBuilder()
                            .winnerTeamId(winnerTeamId)
                            .status(MatchStatus.COMPLETED)
                            .build();

                    return matchRepository.update(updatedMatch);
                });
    }
}


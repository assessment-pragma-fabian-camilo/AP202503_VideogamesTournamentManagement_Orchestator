package com.fc2o.usecase.match;

import com.fc2o.model.match.Match;
import com.fc2o.model.match.gateways.MatchRepository;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

/**
 * Use case for removing a team from a match.
 * Business rule: Only PROMOTER, MODERATOR or ADMIN can remove teams.
 */
public class RemoveTeamFromMatchUseCase {
    private final MatchRepository matchRepository;

    public RemoveTeamFromMatchUseCase(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public Mono<Match> execute(String matchId, String teamId) {
        if (matchId == null || matchId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Match ID is required"));
        }
        if (teamId == null || teamId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Team ID is required"));
        }

        return matchRepository.findById(matchId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Match not found")))
                .flatMap(match -> {
                    Set<String> participants = match.participantIds();
                    if (participants == null || !participants.contains(teamId)) {
                        return Mono.error(new IllegalArgumentException("Team not assigned to this match"));
                    }

                    participants.remove(teamId);
                    Match updatedMatch = match.toBuilder()
                            .participantIds(participants)
                            .build();

                    return matchRepository.update(updatedMatch);
                });
    }
}


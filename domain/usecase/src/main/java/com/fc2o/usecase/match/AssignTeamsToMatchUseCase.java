package com.fc2o.usecase.match;

import com.fc2o.model.match.Match;
import com.fc2o.model.match.gateways.MatchRepository;
import com.fc2o.usecase.tournamentteam.CheckTeamInTournamentUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

/**
 * Use case for assigning teams to a match.
 * Business rules:
 * - Only PROMOTER, MODERATOR or ADMIN can assign teams
 * - Can only assign teams for ONGOING tournaments
 * - Team must be inscribed in the tournament (ACTIVE/USED participation_ticket)
 * - A team cannot already be assigned to this match
 */
@RequiredArgsConstructor
public class AssignTeamsToMatchUseCase {
    private final MatchRepository matchRepository;
    private final CheckTeamInTournamentUseCase checkTeamInTournamentUseCase;

    public Mono<Match> execute(String matchId, String teamId) {
        if (matchId == null || matchId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Match ID is required"));
        }
        if (teamId == null || teamId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Team ID is required"));
        }

        return matchRepository.findById(matchId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Match not found with ID: " + matchId)))
                .flatMap(match -> checkTeamInTournamentUseCase.execute(match.tournamentId(), teamId)
                        .flatMap(isTeamInTournament -> {
                            if (!isTeamInTournament) {
                                return Mono.error(new IllegalArgumentException(
                                        "Team is not inscribed in the tournament for this match"
                                ));
                            }

                            Set<String> participants = match.participantIds();
                            if (participants == null) {
                                participants = new HashSet<>();
                            }

                            if (participants.contains(teamId)) {
                                return Mono.error(new IllegalArgumentException("Team is already assigned to this match"));
                            }

                            participants.add(teamId);
                            Match updatedMatch = match.toBuilder()
                                    .participantIds(participants)
                                    .build();

                            return matchRepository.update(updatedMatch);
                        }));
    }
}


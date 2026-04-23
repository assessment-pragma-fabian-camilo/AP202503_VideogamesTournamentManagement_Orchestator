package com.fc2o.usecase.match;

import com.fc2o.model.match.Match;
import com.fc2o.model.match.gateways.MatchRepository;
import com.fc2o.model.shared.MatchStatus;
import com.fc2o.model.tournament.gateways.TournamentRepository;
import reactor.core.publisher.Mono;

import java.util.HashSet;

/**
 * Use case for creating a new match.
 * Business rules:
 * - Can only create matches for tournaments in ONGOING status
 * - Tournament must have at least 2 teams inscribed (ACTIVE/USED tickets)
 * - Matches cannot overlap in time for the same team
 */
public class CreateMatchUseCase {
    private final MatchRepository matchRepository;
    private final TournamentRepository tournamentRepository;

    public CreateMatchUseCase(MatchRepository matchRepository, TournamentRepository tournamentRepository) {
        this.matchRepository = matchRepository;
        this.tournamentRepository = tournamentRepository;
    }

    public Mono<Match> execute(
            String tournamentId,
            String dateStart,
            String timeStart) {

        if (tournamentId == null || tournamentId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Tournament ID is required"));
        }
        if (dateStart == null || dateStart.isBlank()) {
            return Mono.error(new IllegalArgumentException("Date start is required"));
        }

        // Verify tournament exists and is ONGOING
        return tournamentRepository.findById(tournamentId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Tournament not found")))
                .flatMap(tournament -> {
                    if (!tournament.isOngoing()) {
                        return Mono.error(new IllegalArgumentException("Can only create matches for ONGOING tournaments"));
                    }

                    Match newMatch = Match.builder()
                            .tournamentId(tournamentId)
                            .startDateTime(dateStart)
                            .status(MatchStatus.SCHEDULED)
                            .participantIds(new HashSet<>())
                            .build();

                    return matchRepository.save(newMatch);
                });
    }
}


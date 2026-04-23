package com.fc2o.usecase.match;

import com.fc2o.model.match.Match;
import com.fc2o.model.match.gateways.MatchRepository;
import reactor.core.publisher.Flux;

/**
 * Use case for listing matches by tournament or all matches.
 */
public class ListMatchesUseCase {
    private final MatchRepository matchRepository;

    public ListMatchesUseCase(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    /**
     * List all matches.
     */
    public Flux<Match> executeAll() {
        return matchRepository.findAll();
    }

    /**
     * List matches for a specific tournament.
     */
    public Flux<Match> executeByTournament(String tournamentId) {
        if (tournamentId == null || tournamentId.isBlank()) {
            return Flux.error(new IllegalArgumentException("Tournament ID is required"));
        }
        return matchRepository.findAllByTournamentId(tournamentId);
    }

    /**
     * List matches for a specific participant in a tournament.
     */
    public Flux<Match> executeByParticipantAndTournament(String participantId, String tournamentId) {
        if (participantId == null || participantId.isBlank()) {
            return Flux.error(new IllegalArgumentException("Participant ID is required"));
        }
        if (tournamentId == null || tournamentId.isBlank()) {
            return Flux.error(new IllegalArgumentException("Tournament ID is required"));
        }
        return matchRepository.findAllByParticipantIdAndTournamentId(participantId, tournamentId);
    }
}


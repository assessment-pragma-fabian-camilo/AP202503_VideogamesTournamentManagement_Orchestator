package com.fc2o.usecase.tournamentteam;

import com.fc2o.model.tournamentteam.TournamentTeam;
import com.fc2o.model.tournamentteam.gateways.TournamentTeamRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Use case: Check if a team is registered in a tournament.
 * Internal operation used by other use cases to validate team registration.
 */
@RequiredArgsConstructor
public class CheckTeamInTournamentUseCase {

    private final TournamentTeamRepository tournamentTeamRepository;

    public Mono<Boolean> execute(String tournamentId, String teamId) {
        return tournamentTeamRepository.existsByTournamentIdAndTeamId(tournamentId, teamId);
    }
}



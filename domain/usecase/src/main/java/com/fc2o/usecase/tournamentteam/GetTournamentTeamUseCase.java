package com.fc2o.usecase.tournamentteam;

import com.fc2o.model.tournamentteam.TournamentTeam;
import com.fc2o.model.tournamentteam.gateways.TournamentTeamRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Use case: Get a specific team in a tournament.
 * Public operation - any authenticated user can retrieve details.
 */
@RequiredArgsConstructor
public class GetTournamentTeamUseCase {

    private final TournamentTeamRepository tournamentTeamRepository;

    public Mono<TournamentTeam> execute(String tournamentId, String teamId) {
        return tournamentTeamRepository.findByTournamentIdAndTeamId(tournamentId, teamId);
    }
}



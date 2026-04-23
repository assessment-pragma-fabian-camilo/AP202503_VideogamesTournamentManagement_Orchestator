package com.fc2o.usecase.tournamentteam;

import com.fc2o.model.tournamentteam.TournamentTeam;
import com.fc2o.model.tournamentteam.gateways.TournamentTeamRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Use case: List all teams registered in a tournament.
 * Public operation - any authenticated user can list tournament teams.
 */
@RequiredArgsConstructor
public class ListTournamentTeamsUseCase {

    private final TournamentTeamRepository tournamentTeamRepository;

    public Flux<TournamentTeam> execute(String tournamentId) {
        return tournamentTeamRepository.findByTournamentId(tournamentId);
    }
}



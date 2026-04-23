package com.fc2o.usecase.tournamentteam;

import com.fc2o.model.tournamentteam.TournamentTeam;
import com.fc2o.model.tournamentteam.gateways.TournamentTeamRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Use case: Remove a team from a tournament.
 * Restricted operation - only PROMOTER, MOD, and ADMIN can remove teams.
 */
@RequiredArgsConstructor
public class RemoveTournamentTeamUseCase {

    private final TournamentTeamRepository tournamentTeamRepository;

    public Mono<Void> execute(String tournamentId, String teamId) {
        return tournamentTeamRepository.deleteByTournamentIdAndTeamId(tournamentId, teamId);
    }
}



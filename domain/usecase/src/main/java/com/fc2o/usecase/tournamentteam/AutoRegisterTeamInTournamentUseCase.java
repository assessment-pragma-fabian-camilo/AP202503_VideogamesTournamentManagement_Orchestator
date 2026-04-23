package com.fc2o.usecase.tournamentteam;

import com.fc2o.model.tournamentteam.TournamentTeam;
import com.fc2o.model.tournamentteam.gateways.TournamentTeamRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Use case: Auto-register a team in a tournament.
 * Internal operation triggered when a participation_ticket transitions to USED.
 * This is called by the participation_ticket adapter, not exposed via API.
 */
@RequiredArgsConstructor
public class AutoRegisterTeamInTournamentUseCase {

    private final TournamentTeamRepository tournamentTeamRepository;

    /**
     * Creates a tournament_team registration.
     * Should be called when a participation_ticket reaches USED status.
     */
    public Mono<TournamentTeam> execute(String tournamentId, String teamId) {
        TournamentTeam tournamentTeam = TournamentTeam.builder()
                .tournamentId(tournamentId)
                .teamId(teamId)
                .build();

        return tournamentTeamRepository.save(tournamentTeam);
    }
}



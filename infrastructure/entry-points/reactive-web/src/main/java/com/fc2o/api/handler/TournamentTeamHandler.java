package com.fc2o.api.handler;

import com.fc2o.api.dto.response.tournamentteam.TournamentTeamResponse;
import com.fc2o.api.mapper.tournamentteam.TournamentTeamMapper;
import com.fc2o.usecase.tournamentteam.CheckTeamInTournamentUseCase;
import com.fc2o.usecase.tournamentteam.GetTournamentTeamUseCase;
import com.fc2o.usecase.tournamentteam.ListTournamentTeamsUseCase;
import com.fc2o.usecase.tournamentteam.RemoveTournamentTeamUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Handler for tournament team endpoints.
 * Manages operations on teams registered in tournaments.
 */
@Component
@RequiredArgsConstructor
public class TournamentTeamHandler {

    private final ListTournamentTeamsUseCase listTournamentTeamsUseCase;
    private final GetTournamentTeamUseCase getTournamentTeamUseCase;
    private final RemoveTournamentTeamUseCase removeTournamentTeamUseCase;
    private final CheckTeamInTournamentUseCase checkTeamInTournamentUseCase;
    private final TournamentTeamMapper mapper;

    /**
     * List all teams in a tournament.
     * GET /api/v1/tournament-teams/tournament/{tournamentId}
     */
    public Mono<ServerResponse> listTeamsInTournament(ServerRequest request) {
        String tournamentId = request.pathVariable("tournamentId");

        return ServerResponse.ok()
                .body(listTournamentTeamsUseCase.execute(tournamentId)
                        .map(mapper::toResponse),
                        TournamentTeamResponse.class);
    }

    /**
     * Get a specific team in a tournament.
     * GET /api/v1/tournament-teams/tournament/{tournamentId}/team/{teamId}
     */
    public Mono<ServerResponse> getTeamInTournament(ServerRequest request) {
        String tournamentId = request.pathVariable("tournamentId");
        String teamId = request.pathVariable("teamId");

        return getTournamentTeamUseCase.execute(tournamentId, teamId)
                .flatMap(team -> ServerResponse.ok()
                        .bodyValue(mapper.toResponse(team)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    /**
     * Remove a team from a tournament.
     * DELETE /api/v1/tournament-teams/tournament/{tournamentId}/team/{teamId}
     * Requires: PROMOTER, MOD or ADMIN role
     */
    @PreAuthorize("hasAnyRole('PROMOTER', 'MOD', 'ADMIN')")
    public Mono<ServerResponse> removeTeamFromTournament(ServerRequest request) {
        String tournamentId = request.pathVariable("tournamentId");
        String teamId = request.pathVariable("teamId");

        return removeTournamentTeamUseCase.execute(tournamentId, teamId)
                .flatMap(v -> ServerResponse.noContent().build());
    }

    /**
     * Check if a team is registered in a tournament.
     * GET /api/v1/tournament-teams/tournament/{tournamentId}/team/{teamId}/exists
     * Internal use case.
     */
    public Mono<ServerResponse> checkTeamInTournament(ServerRequest request) {
        String tournamentId = request.pathVariable("tournamentId");
        String teamId = request.pathVariable("teamId");

        return checkTeamInTournamentUseCase.execute(tournamentId, teamId)
                .flatMap(exists -> ServerResponse.ok()
                        .bodyValue(exists));
    }
}



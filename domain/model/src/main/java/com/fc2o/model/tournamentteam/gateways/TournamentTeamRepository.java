package com.fc2o.model.tournamentteam.gateways;

import com.fc2o.model.tournamentteam.TournamentTeam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Gateway interface for TournamentTeam repository operations.
 * Manages the relationship between teams and tournaments.
 */
public interface TournamentTeamRepository {

    /**
     * Find all teams in a tournament.
     */
    Flux<TournamentTeam> findByTournamentId(String tournamentId);

    /**
     * Find a specific team in a tournament.
     */
    Mono<TournamentTeam> findByTournamentIdAndTeamId(String tournamentId, String teamId);

    /**
     * Create a tournament team registration.
     * Automatically called when a participation_ticket transitions to USED.
     */
    Mono<TournamentTeam> save(TournamentTeam tournamentTeam);

    /**
     * Update a tournament team.
     */
    Mono<TournamentTeam> update(TournamentTeam tournamentTeam);

    /**
     * Remove a team from a tournament.
     */
    Mono<Void> deleteByTournamentIdAndTeamId(String tournamentId, String teamId);

    /**
     * Check if a team is registered in a tournament.
     */
    Mono<Boolean> existsByTournamentIdAndTeamId(String tournamentId, String teamId);
}


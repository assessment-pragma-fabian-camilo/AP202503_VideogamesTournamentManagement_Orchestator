package com.fc2o.supabase.tournamentteam;

import com.fc2o.model.tournamentteam.TournamentTeam;
import com.fc2o.model.tournamentteam.gateways.TournamentTeamRepository;
import com.fc2o.supabase.client.SupabaseRestClient;
import com.fc2o.supabase.tournamentteam.dto.TournamentTeamRowDto;
import com.fc2o.supabase.tournamentteam.mapper.SupabaseTournamentTeamMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Supabase adapter for TournamentTeam repository.
 * Handles all database operations for tournament_teams table.
 */
@Repository
@RequiredArgsConstructor
public class TournamentTeamSupabaseAdapter implements TournamentTeamRepository {

    private static final String TABLE = "tournament_teams";

    private final SupabaseRestClient supabaseRestClient;
    private final SupabaseTournamentTeamMapper mapper;

    @Override
    public Flux<TournamentTeam> findByTournamentId(String tournamentId) {
        return supabaseRestClient.select(TABLE, Map.of("tournament_id", tournamentId), TournamentTeamRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<TournamentTeam> findByTournamentIdAndTeamId(String tournamentId, String teamId) {
        return supabaseRestClient.select(
                TABLE,
                Map.of("tournament_id", tournamentId, "team_id", teamId),
                TournamentTeamRowDto.class
        )
        .next()
        .map(mapper::toDomain);
    }

    @Override
    public Mono<TournamentTeam> save(TournamentTeam tournamentTeam) {
        return supabaseRestClient.insert(TABLE, mapper.toDto(tournamentTeam), TournamentTeamRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<TournamentTeam> update(TournamentTeam tournamentTeam) {
        return supabaseRestClient.updateById(TABLE, tournamentTeam.id(), mapper.toDto(tournamentTeam), TournamentTeamRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteByTournamentIdAndTeamId(String tournamentId, String teamId) {
        return supabaseRestClient.select(
                TABLE,
                Map.of("tournament_id", tournamentId, "team_id", teamId),
                TournamentTeamRowDto.class
        )
        .next()
        .flatMap(dto -> supabaseRestClient.deleteById(TABLE, dto.getId()));
    }

    @Override
    public Mono<Boolean> existsByTournamentIdAndTeamId(String tournamentId, String teamId) {
        return supabaseRestClient.select(
                TABLE,
                Map.of("tournament_id", tournamentId, "team_id", teamId),
                TournamentTeamRowDto.class
        )
        .count()
        .map(count -> count > 0);
    }
}



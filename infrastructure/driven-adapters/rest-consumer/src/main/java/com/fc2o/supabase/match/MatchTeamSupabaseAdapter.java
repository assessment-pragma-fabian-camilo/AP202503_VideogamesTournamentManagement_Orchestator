package com.fc2o.supabase.match;

import com.fc2o.model.matchteam.MatchTeam;
import com.fc2o.model.matchteam.gateways.MatchTeamRepository;
import com.fc2o.supabase.client.SupabaseRestClient;
import com.fc2o.supabase.match.dto.MatchTeamRowDto;
import com.fc2o.supabase.match.mapper.SupabaseMatchTeamMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class MatchTeamSupabaseAdapter implements MatchTeamRepository {

    private static final String TABLE = "match_teams";

    private final SupabaseRestClient supabaseRestClient;
    private final SupabaseMatchTeamMapper mapper;


    @Override
    public Flux<MatchTeam> findAllByMatchId(String matchId) {
        return supabaseRestClient.select(TABLE, Map.of("match_id", matchId), MatchTeamRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByMatchIdAndTeamId(String matchId, String teamId) {
        return supabaseRestClient.select(TABLE, Map.of("match_id", matchId, "team_id", teamId), MatchTeamRowDto.class)
                .next()
                .map(dto -> true)
                .defaultIfEmpty(false);
    }

    @Override
    public Mono<MatchTeam> save(MatchTeam matchTeam) {
        return supabaseRestClient.insert(TABLE, mapper.toDto(matchTeam), MatchTeamRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(String matchTeamId) {
        return supabaseRestClient.deleteById(TABLE, matchTeamId);
    }

}


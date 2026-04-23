package com.fc2o.supabase.team;

import com.fc2o.model.team.Team;
import com.fc2o.model.team.gateways.TeamRepository;
import com.fc2o.supabase.client.SupabaseRestClient;
import com.fc2o.supabase.team.dto.TeamRowDto;
import com.fc2o.supabase.team.mapper.SupabaseTeamMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class TeamSupabaseAdapter implements TeamRepository {

    private static final String TABLE = "teams";

    private final SupabaseRestClient supabaseRestClient;
    private final SupabaseTeamMapper mapper;

    @Override
    public Mono<Team> findById(String teamId) {
        return supabaseRestClient.select(TABLE, Map.of("id", teamId), TeamRowDto.class)
                .next()
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Team> findAll() {
        return supabaseRestClient.select(TABLE, null, TeamRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Team> findByLeaderUserId(String leaderUserId) {
        return supabaseRestClient.select(TABLE, Map.of("leader_user_id", leaderUserId), TeamRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Team> save(Team team) {
        return supabaseRestClient.insert(TABLE, mapper.toDto(team), TeamRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Team> update(Team team) {
        return null;
    }

    @Override
    public Mono<Void> deleteById(String teamId) {
        return supabaseRestClient.deleteById(TABLE, teamId);
    }
}


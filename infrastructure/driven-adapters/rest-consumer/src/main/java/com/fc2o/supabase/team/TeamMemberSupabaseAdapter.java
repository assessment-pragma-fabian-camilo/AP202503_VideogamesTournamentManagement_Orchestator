package com.fc2o.supabase.team;

import com.fc2o.model.team.TeamMember;
import com.fc2o.model.team.gateways.TeamMemberRepository;
import com.fc2o.supabase.client.SupabaseRestClient;
import com.fc2o.supabase.team.dto.TeamMemberRowDto;
import com.fc2o.supabase.team.mapper.SupabaseTeamMemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class TeamMemberSupabaseAdapter implements TeamMemberRepository {

    private static final String TABLE = "team_members";

    private final SupabaseRestClient supabaseRestClient;
    private final SupabaseTeamMemberMapper mapper;

    @Override
    public Flux<TeamMember> findAllByTeamId(String teamId) {
        return supabaseRestClient.select(TABLE, Map.of("team_id", teamId), TeamMemberRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<TeamMember> findAllByUserId(String userId) {
        return supabaseRestClient.select(TABLE, Map.of("user_id", userId), TeamMemberRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByTeamIdAndUserId(String teamId, String userId) {
        return supabaseRestClient.select(TABLE, Map.of("team_id", teamId, "user_id", userId), TeamMemberRowDto.class)
                .next()
                .map(dto -> true)
                .defaultIfEmpty(false);
    }

    @Override
    public Mono<TeamMember> save(TeamMember teamMember) {
        return supabaseRestClient.insert(TABLE, mapper.toDto(teamMember), TeamMemberRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteByTeamIdAndUserId(String teamId, String userId) {
        // TODO: This needs better implementation since Supabase doesn't support composite key filtering directly
        return Mono.empty();
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return null;
    }
}


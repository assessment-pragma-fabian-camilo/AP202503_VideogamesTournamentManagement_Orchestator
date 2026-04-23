package com.fc2o.supabase.reward;

import com.fc2o.model.reward.Reward;
import com.fc2o.model.reward.gateways.RewardRepository;
import com.fc2o.supabase.client.SupabaseRestClient;
import com.fc2o.supabase.reward.dto.RewardRowDto;
import com.fc2o.supabase.reward.mapper.SupabaseRewardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class RewardSupabaseAdapter implements RewardRepository {

    private static final String TABLE = "rewards";

    private final SupabaseRestClient supabaseRestClient;
    private final SupabaseRewardMapper mapper;

    @Override
    public Mono<Reward> findById(String id) {
        return supabaseRestClient.select(TABLE, Map.of("id", id), RewardRowDto.class)
                .next()
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Reward> findAll() {
        return null;
    }

    @Override
    public Flux<Reward> findAllByTournamentId(String tournamentId) {
        return supabaseRestClient.select(TABLE, Map.of("tournament_id", tournamentId), RewardRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Reward> findByTournamentIdAndPosition(String tournamentId, Short position) {
        return null;
    }

    @Override
    public Mono<Reward> save(Reward reward) {
        return supabaseRestClient.insert(TABLE, mapper.toDto(reward), RewardRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Reward> update(Reward reward) {
        return supabaseRestClient.updateById(TABLE, reward.id(), mapper.toDto(reward), RewardRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return null;
    }
}


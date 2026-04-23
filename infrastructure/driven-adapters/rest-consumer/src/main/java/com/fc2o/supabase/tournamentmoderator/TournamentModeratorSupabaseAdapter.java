package com.fc2o.supabase.tournamentmoderator;

import com.fc2o.model.tournamentmoderator.TournamentModerator;
import com.fc2o.model.tournamentmoderator.gateways.TournamentModeratorRepository;
import com.fc2o.supabase.client.SupabaseRestClient;
import com.fc2o.supabase.tournamentmoderator.dto.TournamentModeratorRowDto;
import com.fc2o.supabase.tournamentmoderator.mapper.SupabaseTournamentModeratorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class TournamentModeratorSupabaseAdapter implements TournamentModeratorRepository {

    private static final String TABLE = "tournament_moderators";

    private final SupabaseRestClient supabaseRestClient;
    private final SupabaseTournamentModeratorMapper mapper;

    @Override
    public Flux<TournamentModerator> findAllByTournamentId(String tournamentId) {
        return supabaseRestClient.select(TABLE, Map.of("tournament_id", tournamentId), TournamentModeratorRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByTournamentIdAndUserId(String tournamentId, String userId) {
        return supabaseRestClient.select(TABLE, Map.of("tournament_id", tournamentId, "user_id", userId), TournamentModeratorRowDto.class)
                .next()
                .map(dto -> true)
                .defaultIfEmpty(false);
    }

    @Override
    public Mono<TournamentModerator> save(TournamentModerator tournamentModerator) {
        return supabaseRestClient.insert(TABLE, mapper.toDto(tournamentModerator), TournamentModeratorRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteByTournamentIdAndUserId(String tournamentId, String userId) {
        // TODO: This needs better implementation since Supabase doesn't support composite key filtering directly
        return Mono.empty();
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return null;
    }
}


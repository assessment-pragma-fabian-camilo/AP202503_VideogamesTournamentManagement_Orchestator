package com.fc2o.supabase.match;

import com.fc2o.model.match.Match;
import com.fc2o.model.match.gateways.MatchRepository;
import com.fc2o.model.shared.MatchStatus;
import com.fc2o.supabase.client.SupabaseRestClient;
import com.fc2o.supabase.match.dto.MatchRowDto;
import com.fc2o.supabase.match.mapper.SupabaseMatchMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class MatchSupabaseAdapter implements MatchRepository {

    private static final String TABLE = "matches";

    private final SupabaseRestClient supabaseRestClient;
    private final SupabaseMatchMapper mapper;

    @Override
    public Mono<Match> findById(String id) {
        return supabaseRestClient.select(TABLE, Map.of("id", id), MatchRowDto.class)
                .next()
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Match> findAll() {
        return supabaseRestClient.select(TABLE, null, MatchRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Match> findAllByTournamentId(String tournamentId) {
        return supabaseRestClient.select(TABLE, Map.of("tournament_id", tournamentId), MatchRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Match> findAllByParticipantIdAndTournamentId(String participantId, String tournamentId) {
        // TODO: This requires joining with match_teams table
        // For now, fetch all matches in tournament and filter client-side
        return findAllByTournamentId(tournamentId);
    }

    @Override
    public Flux<Match> findAllByStatus(MatchStatus status) {
        return null;
    }

    @Override
    public Mono<Match> save(Match match) {
        return supabaseRestClient.insert(TABLE, mapper.toDto(match), MatchRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Match> update(Match match) {
        return supabaseRestClient.updateById(TABLE, match.id(), mapper.toDto(match), MatchRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Match> patch(Match match) {
        return update(match);
    }

    @Override
    public Mono<Match> deleteById(String id) {
        return supabaseRestClient.deleteById(TABLE, id)
                .then(Mono.empty());
    }
}


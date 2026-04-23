package com.fc2o.supabase.tournament;

import com.fc2o.model.shared.TournamentStatus;
import com.fc2o.model.tournament.Tournament;
import com.fc2o.model.tournament.gateways.TournamentRepository;
import com.fc2o.supabase.client.SupabaseRestClient;
import com.fc2o.supabase.tournament.dto.TournamentRowDto;
import com.fc2o.supabase.tournament.mapper.SupabaseTournamentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class TournamentSupabaseAdapter implements TournamentRepository {

    private static final String TABLE = "tournaments";

    private final SupabaseRestClient supabaseRestClient;
    private final SupabaseTournamentMapper mapper;

    @Override
    public Mono<Tournament> findById(String id) {
        return supabaseRestClient.select(TABLE, Map.of("id", id), TournamentRowDto.class)
                .next()
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Tournament> findAll() {
        return supabaseRestClient.select(TABLE, null, TournamentRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Tournament> save(Tournament tournament) {
        return supabaseRestClient.insert(TABLE, mapper.toDto(tournament), TournamentRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Tournament> update(Tournament tournament) {
        return supabaseRestClient.updateById(TABLE, tournament.id(), mapper.toDto(tournament), TournamentRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Tournament> patch(Tournament tournament) {
        return update(tournament);
    }

    @Override
    public Mono<Tournament> deleteById(String id) {
        return supabaseRestClient.deleteById(TABLE, id)
                .then(Mono.empty());
    }

    @Override
    public Flux<Tournament> findAllByPromoterId(String promoterId) {
        return supabaseRestClient.select(TABLE, Map.of("promoter_user_id", promoterId), TournamentRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Tournament> findAllByStatus(TournamentStatus status) {
        return null;
    }

    @Override
    public Mono<Tournament> patchRegisterParticipant(String tournamentId, String participantId) {
        // TODO: Implement participant registration logic
        return findById(tournamentId);
    }

    @Override
    public Mono<Tournament> patchDisqualify(String tournamentId, String participantId) {
        // TODO: Implement disqualification logic
        return findById(tournamentId);
    }

    @Override
    public Mono<Tournament> patchPreRegisterParticipant(String tournamentId, String participantId) {
        // TODO: Implement pre-registration logic
        return findById(tournamentId);
    }

    @Override
    public Mono<Tournament> patchUnregisterParticipant(String tournamentId, String participantId) {
        return null;
    }
}


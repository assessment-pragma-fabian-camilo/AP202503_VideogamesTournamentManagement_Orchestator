package com.fc2o.supabase.stream;

import com.fc2o.model.stream.Stream;
import com.fc2o.model.stream.StreamType;
import com.fc2o.model.stream.gateways.StreamRepository;
import com.fc2o.supabase.client.SupabaseRestClient;
import com.fc2o.supabase.stream.dto.StreamRowDto;
import com.fc2o.supabase.stream.mapper.SupabaseStreamMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class StreamSupabaseAdapter implements StreamRepository {

    private static final String TABLE = "streams";

    private final SupabaseRestClient supabaseRestClient;
    private final SupabaseStreamMapper mapper;

    @Override
    public Mono<Stream> findById(String id) {
        return null;
    }

    @Override
    public Flux<Stream> findAllByTournamentId(String tournamentId) {
        return supabaseRestClient.select(TABLE, Map.of("tournament_id", tournamentId), StreamRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Stream> findAllByType(StreamType type) {
        return null;
    }

    @Override
    public Mono<Stream> save(Stream transmission) {
        return supabaseRestClient.insert(TABLE, mapper.toDto(transmission), StreamRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Stream> update(Stream transmission) {
        return supabaseRestClient.updateById(TABLE, transmission.id(), mapper.toDto(transmission), StreamRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return supabaseRestClient.deleteById(TABLE, id);
    }
}


package com.fc2o.supabase.game;

import com.fc2o.model.game.Game;
import com.fc2o.model.game.gateways.GameRepository;
import com.fc2o.supabase.client.SupabaseRestClient;
import com.fc2o.supabase.game.dto.GameRowDto;
import com.fc2o.supabase.game.mapper.SupabaseGameMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class GameSupabaseAdapter implements GameRepository {

    private static final String TABLE = "games";

    private final SupabaseRestClient supabaseRestClient;
    private final SupabaseGameMapper mapper;

    @Override
    public Mono<Game> findById(String id) {
        return supabaseRestClient.select(TABLE, Map.of("id", id), GameRowDto.class)
                .next()
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Game> findAll() {
        return supabaseRestClient.select(TABLE, Map.of(), GameRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Game> findByName(String name) {
        return null;
    }

    @Override
    public Mono<Game> save(Game game) {
        return supabaseRestClient.insert(TABLE, mapper.toDto(game), GameRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Game> update(Game game) {
        return supabaseRestClient.updateById(TABLE, game.id(), mapper.toDto(game), GameRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Game> patch(Game game) {
        return update(game);
    }

    @Override
    public Mono<Game> deleteById(String id) {
        return findById(id).flatMap(game -> supabaseRestClient.deleteById(TABLE, id).thenReturn(game));
    }
}


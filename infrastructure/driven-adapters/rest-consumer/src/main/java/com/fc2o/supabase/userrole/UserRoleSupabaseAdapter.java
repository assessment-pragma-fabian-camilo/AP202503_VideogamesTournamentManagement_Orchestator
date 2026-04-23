package com.fc2o.supabase.userrole;

import com.fc2o.model.userrole.Role;
import com.fc2o.model.userrole.UserRole;
import com.fc2o.model.userrole.gateways.UserRoleRepository;
import com.fc2o.supabase.client.SupabaseRestClient;
import com.fc2o.supabase.userrole.dto.UserRoleRowDto;
import com.fc2o.supabase.userrole.mapper.SupabaseUserRoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class UserRoleSupabaseAdapter implements UserRoleRepository {

    private static final String TABLE = "user_roles";

    private final SupabaseRestClient supabaseRestClient;
    private final SupabaseUserRoleMapper mapper;

    @Override
    public Flux<UserRole> findAllByUserId(String userId) {
        return supabaseRestClient.select(TABLE, Map.of("user_id", userId), UserRoleRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByUserIdAndRole(String userId, Role role) {
        return supabaseRestClient.select(TABLE, Map.of("user_id", userId, "role", role.name()), UserRoleRowDto.class)
                .next()
                .map(dto -> true)
                .defaultIfEmpty(false);
    }

    @Override
    public Mono<UserRole> save(UserRole userRole) {
        return supabaseRestClient.insert(TABLE, mapper.toDto(userRole), UserRoleRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteByUserIdAndRole(String userId, Role role) {
        // TODO: This needs better implementation since Supabase doesn't support composite key filtering directly
        return Mono.empty();
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return null;
    }

}


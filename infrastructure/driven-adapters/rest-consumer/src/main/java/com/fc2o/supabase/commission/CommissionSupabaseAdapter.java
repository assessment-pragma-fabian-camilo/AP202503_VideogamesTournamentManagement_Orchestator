package com.fc2o.supabase.commission;

import com.fc2o.model.commission.Commission;
import com.fc2o.model.commission.gateways.CommissionRepository;
import com.fc2o.model.shared.CommissionType;
import com.fc2o.supabase.client.SupabaseRestClient;
import com.fc2o.supabase.commission.dto.CommissionRowDto;
import com.fc2o.supabase.commission.mapper.SupabaseCommissionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CommissionSupabaseAdapter implements CommissionRepository {

    private static final String TABLE = "commissions";

    private final SupabaseRestClient client;
    private final SupabaseCommissionMapper mapper;


    @Override
    public Mono<Commission> findByCommissionType(CommissionType commissionType) {
        return client.select(TABLE, Map.of("commission_type", commissionType.name()), CommissionRowDto.class)
                .next()
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Commission> findAll() {
        return client.select(TABLE, null, CommissionRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Commission> update(Commission commission) {
        return client.updateById(TABLE, commission.id(), mapper.toDto(commission), CommissionRowDto.class)
                .map(mapper::toDomain);
    }
}


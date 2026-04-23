package com.fc2o.supabase.donation;

import com.fc2o.model.donation.Donation;
import com.fc2o.model.donation.gateways.DonationRepository;
import com.fc2o.model.shared.TransactionStatus;
import com.fc2o.supabase.client.SupabaseRestClient;
import com.fc2o.supabase.donation.dto.DonationRowDto;
import com.fc2o.supabase.donation.mapper.SupabaseDonationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class DonationSupabaseAdapter implements DonationRepository {

    private static final String TABLE = "donations";

    private final SupabaseRestClient supabaseRestClient;
    private final SupabaseDonationMapper mapper;

    @Override
    public Mono<Donation> findById(String id) {
        return supabaseRestClient.select(TABLE, Map.of("id", id), DonationRowDto.class)
                .next()
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Donation> findAll() {
        return supabaseRestClient.select(TABLE, null, DonationRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Donation> findAllByTournamentId(String tournamentId) {
        return null;
    }

    @Override
    public Flux<Donation> findAllByUserId(String userId) {
        return null;
    }

    @Override
    public Flux<Donation> findAllByTeamId(String teamId) {
        return null;
    }

    @Override
    public Flux<Donation> findAllByStatus(TransactionStatus status) {
        return null;
    }

    @Override
    public Mono<Donation> save(Donation donation) {
        return supabaseRestClient.insert(TABLE, mapper.toDto(donation), DonationRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Donation> update(Donation donation) {
        return supabaseRestClient.updateById(TABLE, donation.id(), mapper.toDto(donation), DonationRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Donation> patch(Donation donation) {
        return update(donation);
    }

    @Override
    public Mono<Donation> deleteById(String id) {
        return supabaseRestClient.deleteById(TABLE, id)
                .then(Mono.empty());
    }
}


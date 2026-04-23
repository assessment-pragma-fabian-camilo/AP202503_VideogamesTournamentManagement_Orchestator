package com.fc2o.supabase.participationticket;

import com.fc2o.model.participationticket.ParticipationTicket;
import com.fc2o.model.participationticket.gateways.ParticipationTicketRepository;
import com.fc2o.model.shared.TicketStatus;
import com.fc2o.model.shared.TransactionStatus;
import com.fc2o.supabase.client.SupabaseRestClient;
import com.fc2o.supabase.participationticket.dto.ParticipationTicketRowDto;
import com.fc2o.supabase.participationticket.mapper.SupabaseParticipationTicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ParticipationTicketSupabaseAdapter implements ParticipationTicketRepository {

    private static final String TABLE = "participation_tickets";

    private final SupabaseRestClient supabaseRestClient;
    private final SupabaseParticipationTicketMapper mapper;

    @Override
    public Mono<ParticipationTicket> findById(String ticketId) {
        return supabaseRestClient.select(TABLE, Map.of("id", ticketId), ParticipationTicketRowDto.class)
                .next()
                .map(mapper::toDomain);
    }

    @Override
    public Flux<ParticipationTicket> findAllByTournamentId(String tournamentId) {
        return supabaseRestClient.select(TABLE, Map.of("tournament_id", tournamentId), ParticipationTicketRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<ParticipationTicket> findAllByUserId(String userId) {
        return null;
    }

    @Override
    public Flux<ParticipationTicket> findAllByTeamId(String teamId) {
        return null;
    }

    @Override
    public Flux<ParticipationTicket> findAllByTicketStatus(TicketStatus status) {
        return null;
    }

    @Override
    public Flux<ParticipationTicket> findAllByTransactionStatus(TransactionStatus status) {
        return null;
    }

    @Override
    public Mono<ParticipationTicket> save(ParticipationTicket participationTicket) {
        return supabaseRestClient.insert(TABLE, mapper.toDto(participationTicket), ParticipationTicketRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<ParticipationTicket> update(ParticipationTicket participationTicket) {
        return supabaseRestClient.updateById(TABLE, participationTicket.id(), mapper.toDto(participationTicket), ParticipationTicketRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(String ticketId) {
        return null;
    }
}


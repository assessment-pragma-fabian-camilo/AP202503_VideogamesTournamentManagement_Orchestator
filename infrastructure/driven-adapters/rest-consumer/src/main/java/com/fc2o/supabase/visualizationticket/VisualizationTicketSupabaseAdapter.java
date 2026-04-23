package com.fc2o.supabase.visualizationticket;

import com.fc2o.model.shared.TicketStatus;
import com.fc2o.model.shared.TransactionStatus;
import com.fc2o.model.visualizationticket.VisualizationTicket;
import com.fc2o.model.visualizationticket.gateways.VisualizationTicketRepository;
import com.fc2o.supabase.client.SupabaseRestClient;
import com.fc2o.supabase.visualizationticket.dto.VisualizationTicketRowDto;
import com.fc2o.supabase.visualizationticket.mapper.SupabaseVisualizationTicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class VisualizationTicketSupabaseAdapter implements VisualizationTicketRepository {

    private static final String TABLE = "visualization_tickets";

    private final SupabaseRestClient supabaseRestClient;
    private final SupabaseVisualizationTicketMapper mapper;

    @Override
    public Mono<VisualizationTicket> findById(String ticketId) {
        return supabaseRestClient.select(TABLE, Map.of("id", ticketId), VisualizationTicketRowDto.class)
                .next()
                .map(mapper::toDomain);
    }

    @Override
    public Flux<VisualizationTicket> findAllByTournamentId(String tournamentId) {
        return supabaseRestClient.select(TABLE, Map.of("tournament_id", tournamentId), VisualizationTicketRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<VisualizationTicket> findAllByUserId(String userId) {
        return null;
    }

    @Override
    public Flux<VisualizationTicket> findAllByTicketStatus(TicketStatus status) {
        return null;
    }

    @Override
    public Flux<VisualizationTicket> findAllByTransactionStatus(TransactionStatus status) {
        return null;
    }

    @Override
    public Mono<VisualizationTicket> save(VisualizationTicket visualizationTicket) {
        return supabaseRestClient.insert(TABLE, mapper.toDto(visualizationTicket), VisualizationTicketRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<VisualizationTicket> update(VisualizationTicket visualizationTicket) {
        return supabaseRestClient.updateById(TABLE, visualizationTicket.id(), mapper.toDto(visualizationTicket), VisualizationTicketRowDto.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(String ticketId) {
        return null;
    }
}


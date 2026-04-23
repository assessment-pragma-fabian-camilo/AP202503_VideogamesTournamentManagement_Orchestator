package com.fc2o.supabase.participationticket.mapper;

import com.fc2o.model.participationticket.ParticipationTicket;
import com.fc2o.supabase.participationticket.dto.ParticipationTicketRowDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface SupabaseParticipationTicketMapper {

    ParticipationTicketRowDto toDto(ParticipationTicket domain);

    ParticipationTicket toDomain(ParticipationTicketRowDto dto);
}
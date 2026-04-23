package com.fc2o.supabase.visualizationticket.mapper;

import com.fc2o.model.visualizationticket.VisualizationTicket;
import com.fc2o.supabase.visualizationticket.dto.VisualizationTicketRowDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface SupabaseVisualizationTicketMapper {

    VisualizationTicketRowDto toDto(VisualizationTicket domain);

    VisualizationTicket toDomain(VisualizationTicketRowDto dto);
}


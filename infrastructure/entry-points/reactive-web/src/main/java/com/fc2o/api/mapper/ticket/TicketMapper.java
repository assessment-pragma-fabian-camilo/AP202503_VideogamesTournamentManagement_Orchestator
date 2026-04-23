package com.fc2o.api.mapper.ticket;

import com.fc2o.api.dto.response.ticket.TicketResponse;
import com.fc2o.model.participationticket.ParticipationTicket;
import com.fc2o.model.visualizationticket.VisualizationTicket;
import org.mapstruct.Mapper;

import java.util.Base64;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface TicketMapper {

    TicketResponse toResponse(ParticipationTicket ticket);

    TicketResponse toResponse(VisualizationTicket ticket);

    default String map(byte[] qr) {
        return qr == null ? null : Base64.getEncoder().encodeToString(qr);
    }
}


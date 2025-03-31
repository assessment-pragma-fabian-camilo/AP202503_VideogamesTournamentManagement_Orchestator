package com.fc2o.airtable.ticket.mapper;

import com.fc2o.airtable.ticket.dto.*;
import com.fc2o.model.ticket.Status;
import com.fc2o.model.ticket.Ticket;
import com.fc2o.model.ticket.Type;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TicketMapper {

  public Ticket toTicket(RecordDto dto) {
    return Ticket.builder()
      .createdTime(dto.createdTime())
      .id(dto.id())
      .customerId(dto.fields().customerId())
      .tournamentId(dto.fields().tournamentId())
      .transactionId(dto.fields().transactionId())
      .type(Type.valueOf(dto.fields().type().name()))
      .status(Status.valueOf(dto.fields().status().name()))
      .qr(dto.fields().qr())
      .build();
  }

  public WrapperDto toWrapperDto(Ticket ticket) {
    return WrapperDto.builder()
      .records(
        List.of(
          RecordDto.builder()
            .id(ticket.id())
            .fields(
              FieldsDto.builder()
                .customerId(ticket.customerId())
                .tournamentId(ticket.tournamentId())
                .transactionId(ticket.transactionId())
                .type(ticket.type() == null ? null : TypeDto.valueOf(ticket.type().name()))
                .status(StatusDto.valueOf(ticket.status().name()))
                .qr(ticket.qr())
                .build()
            )
            .build()
        )
      )
      .build();
  }
}

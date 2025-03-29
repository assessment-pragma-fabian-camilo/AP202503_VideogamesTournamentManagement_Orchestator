package com.fc2o.api.mapper.ticket;

import com.fc2o.api.dto.ticket.res.register.RegisterTicketResponseDto;
import com.fc2o.model.ticket.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface RegisterTicketMapper {
  @Mapping(target = "ticket")
  RegisterTicketResponseDto toRegisterTicketResponseDto(Ticket ticket);
}

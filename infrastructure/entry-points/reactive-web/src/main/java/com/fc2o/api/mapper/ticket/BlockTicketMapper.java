package com.fc2o.api.mapper.ticket;

import com.fc2o.api.dto.ticket.res.block.BlockTicketResponseDto;
import com.fc2o.model.ticket.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface BlockTicketMapper {
  @Mapping(target = "ticket")
  BlockTicketResponseDto toBlockTicketResponseDto(Ticket ticket);
}


package com.fc2o.api.mapper.tournament;

import com.fc2o.api.dto.tournament.res.pre_register_participant.PreRegisterParticipantResponseDto;
import com.fc2o.model.transaction.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface PreRegisterParticipantMapper {
  @Mapping(target = "transaction")
  PreRegisterParticipantResponseDto toPreRegisterParticipantResponseDto(Transaction transaction);
}

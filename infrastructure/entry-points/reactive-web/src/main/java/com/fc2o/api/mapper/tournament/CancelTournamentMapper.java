package com.fc2o.api.mapper.tournament;

import com.fc2o.api.dto.tournament.req.cancel.CancelTournamentRequestDto;
import com.fc2o.api.dto.tournament.res.cancel.CancelTournamentResponseDto;
import com.fc2o.model.tournament.Tournament;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface CancelTournamentMapper {

  @Mapping(source = "tournament", target = ".")
  Tournament toTournament(CancelTournamentRequestDto dto);

  @Mapping(target = "tournament")
  CancelTournamentResponseDto toCancelTournamentResponseDto(Tournament tournament);
}

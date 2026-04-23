package com.fc2o.api.mapper.tournament;

import com.fc2o.api.dto.tournament.req.register.RegisterTournamentRequestDto;
import com.fc2o.api.dto.tournament.res.register.RegisterTournamentResponseDto;
import com.fc2o.model.tournament.Tournament;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.ReportingPolicy.IGNORE;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface RegisterTournamentMapper {

  @Mapping(source = "tournament", target = ".")
  Tournament toTournament(RegisterTournamentRequestDto dto);

  @Mapping(target = "tournament")
  RegisterTournamentResponseDto toTournamentResponseDto(Tournament tournament);
}

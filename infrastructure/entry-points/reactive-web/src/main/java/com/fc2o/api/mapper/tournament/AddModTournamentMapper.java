package com.fc2o.api.mapper.tournament;

import com.fc2o.api.dto.tournament.req.add_mod.AddModTournamentRequestDto;
import com.fc2o.api.dto.tournament.res.add_mod.AddModTournamentResponseDto;
import com.fc2o.model.tournament.Tournament;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface AddModTournamentMapper {

  @Mapping(source = "moderatorIds", target = "moderatorIds")
  Tournament toTournament(AddModTournamentRequestDto dto);

  @Mapping(target = "tournament")
  AddModTournamentResponseDto toAddModTournamentResponseDto(Tournament tournament);
}

package com.fc2o.api.mapper.tournament;

import com.fc2o.api.dto.tournament.res.reschedule.RescheduleTournamentResponseDto;
import com.fc2o.model.tournament.Tournament;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface RescheduleTournamentMapper {
  @Mapping(target = "tournament")
  RescheduleTournamentResponseDto toRescheduleTournamentResponseDto(Tournament tournament);
}
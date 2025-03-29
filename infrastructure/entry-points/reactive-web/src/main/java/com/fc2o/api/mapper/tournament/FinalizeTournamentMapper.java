package com.fc2o.api.mapper.tournament;

import com.fc2o.api.dto.tournament.req.finalize.FinalizeTournamentRequestDto;
import com.fc2o.api.dto.tournament.res.finalize.FinalizeTournamentResponseDto;
import com.fc2o.model.reward.Reward;
import com.fc2o.model.tournament.Tournament;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface FinalizeTournamentMapper {
  @Mapping(source = "reward", target = ".")
  Reward toReward(FinalizeTournamentRequestDto dto);

  @Mapping(source = "tournament", target = "tournament")
  @Mapping(source = "reward", target = "reward")
  FinalizeTournamentResponseDto toFinalizeTournamentResponseDto(Tournament tournament, Reward reward);
}
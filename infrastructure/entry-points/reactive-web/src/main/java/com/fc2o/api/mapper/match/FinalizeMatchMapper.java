package com.fc2o.api.mapper.match;

import com.fc2o.api.dto.match.res.finalize.FinalizeMatchResponseDto;
import com.fc2o.model.match.Match;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface FinalizeMatchMapper {
  @Mapping(target = "match")
  FinalizeMatchResponseDto toFinalizeMatchResponseDto(Match match);
}
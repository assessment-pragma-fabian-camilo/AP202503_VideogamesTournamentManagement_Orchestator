package com.fc2o.api.mapper.match;

import com.fc2o.api.dto.match.res.cancel.CancelMatchResponseDto;
import com.fc2o.model.match.Match;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface CancelMatchMapper {
  @Mapping(target = "match")
  CancelMatchResponseDto toCancelMatchResponseDto(Match match);
}
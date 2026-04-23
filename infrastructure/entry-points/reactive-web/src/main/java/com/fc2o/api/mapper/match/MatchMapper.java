package com.fc2o.api.mapper.match;

import com.fc2o.api.dto.response.match.MatchResponse;
import com.fc2o.model.match.Match;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface MatchMapper {

    MatchResponse toResponse(Match match);
}


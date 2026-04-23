package com.fc2o.api.mapper.tournamentmoderator;

import com.fc2o.api.dto.response.tournamentmoderator.TournamentModeratorResponse;
import com.fc2o.model.tournamentmoderator.TournamentModerator;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface TournamentModeratorMapper {

    TournamentModeratorResponse toResponse(TournamentModerator tournamentModerator);
}


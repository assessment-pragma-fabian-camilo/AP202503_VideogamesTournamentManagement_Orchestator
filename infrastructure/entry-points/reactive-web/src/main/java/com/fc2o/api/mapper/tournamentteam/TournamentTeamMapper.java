package com.fc2o.api.mapper.tournamentteam;

import com.fc2o.api.dto.request.tournamentteam.CreateTournamentTeamRequest;
import com.fc2o.api.dto.response.tournamentteam.TournamentTeamResponse;
import com.fc2o.model.tournamentteam.TournamentTeam;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

/**
 * MapStruct mapper for TournamentTeam DTOs and domain model conversions.
 */
@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface TournamentTeamMapper {

    TournamentTeamResponse toResponse(TournamentTeam domain);

    TournamentTeam toDomain(CreateTournamentTeamRequest request);
}



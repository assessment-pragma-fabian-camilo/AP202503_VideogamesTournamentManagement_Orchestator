package com.fc2o.api.mapper.team;

import com.fc2o.api.dto.response.team.TeamMemberResponse;
import com.fc2o.api.dto.response.team.TeamResponse;
import com.fc2o.model.team.Team;
import com.fc2o.model.team.TeamMember;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface TeamMapper {

    TeamResponse toResponse(Team team);

    TeamMemberResponse toTeamMemberResponse(TeamMember teamMember);
}


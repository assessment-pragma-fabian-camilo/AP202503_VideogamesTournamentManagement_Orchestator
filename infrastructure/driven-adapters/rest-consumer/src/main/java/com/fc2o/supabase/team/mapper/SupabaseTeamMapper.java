package com.fc2o.supabase.team.mapper;

import com.fc2o.model.team.Team;
import com.fc2o.supabase.team.dto.TeamRowDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface SupabaseTeamMapper {

    TeamRowDto toDto(Team domain);

    Team toDomain(TeamRowDto dto);
}

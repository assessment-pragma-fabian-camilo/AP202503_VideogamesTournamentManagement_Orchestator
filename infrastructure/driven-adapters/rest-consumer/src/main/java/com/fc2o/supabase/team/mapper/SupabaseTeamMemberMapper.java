package com.fc2o.supabase.team.mapper;

import com.fc2o.model.team.TeamMember;
import com.fc2o.supabase.team.dto.TeamMemberRowDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface SupabaseTeamMemberMapper {

    TeamMemberRowDto toDto(TeamMember domain);

    TeamMember toDomain(TeamMemberRowDto dto);
}

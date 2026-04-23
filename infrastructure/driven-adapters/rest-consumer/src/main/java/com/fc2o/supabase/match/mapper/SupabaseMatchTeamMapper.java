package com.fc2o.supabase.match.mapper;

import com.fc2o.model.matchteam.MatchTeam;
import com.fc2o.supabase.match.dto.MatchTeamRowDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface SupabaseMatchTeamMapper {

    MatchTeamRowDto toDto(MatchTeam domain);

    MatchTeam toDomain(MatchTeamRowDto dto);
}
package com.fc2o.supabase.match.mapper;

import com.fc2o.model.match.Match;
import com.fc2o.supabase.match.dto.MatchRowDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface SupabaseMatchMapper {

    MatchRowDto toDto(Match domain);

    Match toDomain(MatchRowDto dto);
}
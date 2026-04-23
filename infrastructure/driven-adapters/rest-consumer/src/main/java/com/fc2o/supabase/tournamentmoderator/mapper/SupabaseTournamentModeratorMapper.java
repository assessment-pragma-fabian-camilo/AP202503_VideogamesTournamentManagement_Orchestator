package com.fc2o.supabase.tournamentmoderator.mapper;

import com.fc2o.model.tournamentmoderator.TournamentModerator;
import com.fc2o.supabase.tournamentmoderator.dto.TournamentModeratorRowDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface SupabaseTournamentModeratorMapper {

    TournamentModeratorRowDto toDto(TournamentModerator domain);

    TournamentModerator toDomain(TournamentModeratorRowDto dto);
}


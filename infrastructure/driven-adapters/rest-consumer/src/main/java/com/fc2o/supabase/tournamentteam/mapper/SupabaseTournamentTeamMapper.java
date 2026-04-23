package com.fc2o.supabase.tournamentteam.mapper;

import com.fc2o.model.tournamentteam.TournamentTeam;
import com.fc2o.supabase.tournamentteam.dto.TournamentTeamRowDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface SupabaseTournamentTeamMapper {

    TournamentTeam toDomain(TournamentTeamRowDto dto);

    TournamentTeamRowDto toDto(TournamentTeam domain);
}


package com.fc2o.supabase.tournament.mapper;

import com.fc2o.model.tournament.Tournament;
import com.fc2o.supabase.tournament.dto.TournamentRowDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface SupabaseTournamentMapper {

    TournamentRowDto toDto(Tournament domain);

    Tournament toDomain(TournamentRowDto dto);
}

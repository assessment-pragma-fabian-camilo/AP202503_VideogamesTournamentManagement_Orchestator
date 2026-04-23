package com.fc2o.supabase.game.mapper;

import com.fc2o.model.game.Game;
import com.fc2o.supabase.game.dto.GameRowDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface SupabaseGameMapper {

    GameRowDto toDto(Game domain);

    Game toDomain(GameRowDto dto);
}
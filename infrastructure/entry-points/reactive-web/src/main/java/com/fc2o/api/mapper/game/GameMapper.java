package com.fc2o.api.mapper.game;

import com.fc2o.api.dto.response.game.GameResponse;
import com.fc2o.model.game.Game;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface GameMapper {

    GameResponse toResponse(Game game);
}


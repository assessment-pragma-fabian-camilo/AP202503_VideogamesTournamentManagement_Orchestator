package com.fc2o.airtable.game.mapper;

import com.fc2o.airtable.game.dto.*;
import com.fc2o.model.game.Classification;
import com.fc2o.model.game.Console;
import com.fc2o.model.game.Game;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GameMapper {

  public Game toGame(RecordDto dto) {
    return Game.builder()
      .createdTime(dto.createdTime())
      .id(dto.id())
      .name(dto.fields().name())
      .description(dto.fields().description())
      .categories(dto.fields().categories())
      .consoles(dto.fields().consoles().stream().map(console -> Console.valueOf(console.name())).collect(Collectors.toSet()))
      .releaseDate(dto.fields().releaseDate())
      .classification(Classification.valueOf(dto.fields().classification().name()))
      .build();
  }

  public WrapperDto toWrapperDto(Game game) {
    return WrapperDto.builder()
      .records(
        List.of(
          RecordDto.builder()
            .id(game.id())
            .fields(
              FieldsDto.builder()
                .name(game.name())
                .description(game.description())
                .categories(game.categories())
                .consoles(game.consoles().stream().map(console -> ConsoleDto.valueOf(console.name())).collect(Collectors.toSet()))
                .releaseDate(game.releaseDate())
                .classification(ClassificationDto.valueOf(game.classification().name()))
                .build()
            )
            .build()
        )
      )
      .build();
  }
}

package com.fc2o.airtable.match.mapper;

import com.fc2o.airtable.match.dto.FieldsDto;
import com.fc2o.airtable.match.dto.RecordDto;
import com.fc2o.airtable.match.dto.StatusDto;
import com.fc2o.airtable.match.dto.WrapperDto;
import com.fc2o.model.match.Match;
import com.fc2o.model.match.Status;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MatchMapper {

  public Match toMatch(RecordDto dto) {
    return Match.builder()
      .createdTime(dto.createdTime())
      .id(dto.id())
      .participantIds(dto.fields().participantIds())
      .winnerId(dto.fields().winnerId())
      .tournamentId(dto.fields().tournamentId())
      .timeStart(dto.fields().timeStart())
      .timeEnd(dto.fields().timeEnd())
      .dateStart(dto.fields().dateStart())
      .status(Status.valueOf(dto.fields().status().name()))
      .build();
  }

  public WrapperDto toWrapperDto(Match match) {
    return WrapperDto.builder()
      .records(
        List.of(
          RecordDto.builder()
            .id(match.id())
            .fields(
              FieldsDto.builder()
                .participantIds(match.participantIds())
                .winnerId(match.winnerId())
                .tournamentId(match.tournamentId())
                .timeStart(match.timeStart())
                .timeEnd(match.timeEnd())
                .dateStart(match.dateStart())
                .status(StatusDto.valueOf(match.status().name()))
                .build()
            )
            .build()
        )
      )
      .build();
  }
}

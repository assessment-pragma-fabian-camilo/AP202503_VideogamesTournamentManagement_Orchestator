package com.fc2o.airtable.reward.mapper;

import com.fc2o.airtable.reward.dto.*;
import com.fc2o.model.reward.Reward;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RewardMapper {

  public Reward toReward(RecordDto dto) {
    return Reward.builder()
      .createdTime(dto.createdTime())
      .id(dto.id())
      .standings(dto.fields().standings())
      .prizePerStanding(dto.fields().prizePerStanding())
      .tournamentId(dto.fields().tournamentId())
      .build();
  }

  public WrapperDto toWrapperDto(Reward reward) {
    return WrapperDto.builder()
      .records(
        List.of(
          RecordDto.builder()
            .id(reward.id())
            .fields(
              FieldsDto.builder()
                .standings(reward.standings())
                .prizePerStanding(reward.prizePerStanding())
                .tournamentId(reward.tournamentId())
                .build()
            )
            .build()
        )
      )
      .build();
  }
}

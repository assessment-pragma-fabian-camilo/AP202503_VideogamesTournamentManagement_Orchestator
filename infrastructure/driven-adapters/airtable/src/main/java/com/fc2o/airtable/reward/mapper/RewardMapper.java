package com.fc2o.airtable.reward.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc2o.airtable.reward.dto.*;
import com.fc2o.model.reward.Reward;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RewardMapper {

  public Reward toReward(RecordDto dto) {
    Map<String, BigDecimal> prizePerStanding = new HashMap<>();
    Map<Byte, String> standings = new HashMap<>();
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      prizePerStanding = objectMapper.readValue(dto.fields().prizePerStanding(), new TypeReference<Map<String, BigDecimal>>() {});
      standings = objectMapper.readValue(dto.fields().standings(), new TypeReference<Map<Byte, String>>() {});
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    return Reward.builder()
      .createdTime(dto.createdTime())
      .id(dto.id())
      .standings(standings)
      .prizePerStanding(prizePerStanding)
      .tournamentId(dto.fields().tournamentId())
      .build();
  }

  public WrapperDto toWrapperDto(Reward reward) {
    ObjectMapper objectMapper = new ObjectMapper();
    String prizePerStanding = "";
    String standings = "";
    try {
      prizePerStanding = objectMapper.writeValueAsString(reward.prizePerStanding());
      standings = objectMapper.writeValueAsString(reward.standings());
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    return WrapperDto.builder()
      .records(
        List.of(
          RecordDto.builder()
            .id(reward.id())
            .fields(
              FieldsDto.builder()
                .standings(standings)
                .prizePerStanding(prizePerStanding)
                .tournamentId(reward.tournamentId())
                .build()
            )
            .build()
        )
      )
      .build();
  }
}

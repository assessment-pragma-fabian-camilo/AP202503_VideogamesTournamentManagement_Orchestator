package com.fc2o.airtable.tournament.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc2o.airtable.tournament.dto.*;
import com.fc2o.model.tournament.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TournamentMapper {

  public Tournament toTournament(RecordDto dto) {
    ObjectMapper objectMapper = new ObjectMapper();
    Map<Byte, BigDecimal> pricePerPosition = new HashMap<>();
    List<TransmissionDto> transmissions = new ArrayList<>();
    try {
      pricePerPosition = objectMapper.readValue(dto.fields().prizePerPosition(), new TypeReference<Map<Byte, BigDecimal>>() {});
      transmissions = objectMapper.readValue(dto.fields().transmissions(), new TypeReference<List<TransmissionDto>>() {});
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    return Tournament.builder()
      .createdTime(dto.createdTime())
      .id(dto.id())
      .name(dto.fields().name())
      .isPaid(dto.fields().isPaid() != null && dto.fields().isPaid())
      .status(Status.valueOf(dto.fields().status().name()))
      .placeLimit(dto.fields().placeLimit())
      .placeRemaining(dto.fields().placeRemaining())
      .placeMinimum(dto.fields().placeMinimum())
      .promoterId(dto.fields().promoterId())
      .ruleset(dto.fields().ruleset())
      .gameId(dto.fields().gameId())
      .prizePerPosition(pricePerPosition)
      .dateStart(dto.fields().dateStart())
      .dateEnd(dto.fields().dateEnd())
      .transmissions(
        transmissions.stream()
          .map(transmission ->
            Transmission.builder()
              .url(transmission.url())
              .platform(Platform.valueOf(transmission.platform().name()))
              .build()
          )
          .toList()
      )
      .commission(
        Commission.builder()
          .donationPercentage(dto.fields().commissionDonationPercentage())
          .participationPercentage(dto.fields().commissionParticipationPercentage())
          .visualizationPercentage(dto.fields().commissionVisualizationPercentage())
          .build()
      )
      .price(
        Price.builder()
          .participation(dto.fields().priceParticipation())
          .visualization(dto.fields().priceVisualization())
          .build()
      )
      .preRegisteredParticipantIds(
        dto.fields().preRegisteredParticipantIds() == null ? List.of() : dto.fields().preRegisteredParticipantIds()
      )
      .participantIds(
        dto.fields().participantIds() == null ? List.of() : dto.fields().participantIds()
      )
      .disqualifiedParticipantIds(
        dto.fields().disqualifiedParticipantIds() == null ? List.of() : dto.fields().disqualifiedParticipantIds()
      )
      .moderatorIds(
        dto.fields().moderatorIds() == null ? List.of() : dto.fields().moderatorIds()
      )
      .closedTournamentJustification(dto.fields().closedTournamentJustification())
      .build();
  }

  public WrapperDto toWrapperDto(Tournament tournament) {
    ObjectMapper objectMapper = new ObjectMapper();
    String pricePerPosition = "";
    String transmissions = "";
    try {
      pricePerPosition = objectMapper.writeValueAsString(tournament.prizePerPosition());
      transmissions = objectMapper.writeValueAsString(tournament.transmissions());
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    return WrapperDto.builder()
      .records(
        List.of(
          RecordDto.builder()
            .id(tournament.id())
            .fields(
              FieldsDto.builder()
                .name(tournament.name())
                .isPaid(tournament.isPaid())
                .status(StatusDto.valueOf(tournament.status().name()))
                .placeLimit(tournament.placeLimit())
                .placeRemaining(tournament.placeRemaining())
                .placeMinimum(tournament.placeMinimum())
                .promoterId(tournament.promoterId())
                .ruleset(tournament.ruleset())
                .gameId(tournament.gameId())
                .prizePerPosition(pricePerPosition)
                .dateStart(tournament.dateStart())
                .dateEnd(tournament.dateEnd())
                .transmissions(transmissions)
                .commissionDonationPercentage(tournament.commission().donationPercentage())
                .commissionVisualizationPercentage(tournament.commission().visualizationPercentage())
                .commissionParticipationPercentage(tournament.commission().participationPercentage())
                .priceParticipation(tournament.price().participation())
                .priceVisualization(tournament.price().visualization())
                .preRegisteredParticipantIds(tournament.preRegisteredParticipantIds())
                .participantIds(tournament.participantIds())
                .disqualifiedParticipantIds(tournament.disqualifiedParticipantIds())
                .moderatorIds(tournament.moderatorIds())
                .closedTournamentJustification(tournament.closedTournamentJustification())
                .build()
            )
            .build()
        )
      )
      .typecast(true)
      .build();
  }

  public WrapperDto toPreRegisterWrapperDto(RecordDto tournament, String tournamentId, String participantId) {
    return WrapperDto.builder()
      .records(
        List.of(
          RecordDto.builder()
            .id(tournamentId)
            .fields(
              FieldsDto.builder()
                .preRegisteredParticipantIds(
                  Stream.concat(
                      Stream.ofNullable(tournament.fields().preRegisteredParticipantIds())
                        .flatMap(Collection::stream),
                      Stream.of(participantId)
                    )
                    .collect(Collectors.toSet())
                )
                .placeRemaining((short) (tournament.fields().placeRemaining().intValue() - 1))
                .build()
            )
            .build()
        )
      )
      .typecast(true)
      .build();
  }

  public WrapperDto toDisqualifyWrapperDto(RecordDto tournament, String tournamentId, String participantId) {
    return WrapperDto.builder()
      .records(
        List.of(
          RecordDto.builder()
            .id(tournamentId)
            .fields(
              FieldsDto.builder()
                .preRegisteredParticipantIds(
                  Stream.ofNullable(tournament.fields().preRegisteredParticipantIds())
                    .flatMap(Collection::stream)
                    .filter(s -> !s.equals(participantId))
                    .collect(Collectors.toSet())
                )
                .participantIds(
                  Stream.ofNullable(tournament.fields().participantIds())
                    .flatMap(Collection::stream)
                    .filter(s -> !s.equals(participantId))
                    .collect(Collectors.toSet())
                )
                .disqualifiedParticipantIds(
                  Stream.concat(
                    Stream.ofNullable(tournament.fields().disqualifiedParticipantIds())
                      .flatMap(Collection::stream),
                    Stream.of(participantId)).collect(Collectors.toSet()
                  )
                )
                .build()
            )
            .build()
        )
      )
      .typecast(true)
      .build();
  }

  public WrapperDto toRegisterWrapperDto(RecordDto tournament, String tournamentId, String participantId) {
    return WrapperDto.builder()
      .records(
        List.of(
          RecordDto.builder()
            .id(tournamentId)
            .fields(
              FieldsDto.builder()
                .preRegisteredParticipantIds(
                  Stream.ofNullable(tournament.fields().preRegisteredParticipantIds())
                    .flatMap(Collection::stream)
                    .filter(s -> !s.equals(participantId))
                    .collect(Collectors.toSet())
                )
                .participantIds(
                  Stream.concat(
                    Stream.ofNullable(tournament.fields().participantIds())
                      .flatMap(Collection::stream),
                    Stream.of(participantId)).collect(Collectors.toSet()
                  )
                )
                .build()
            )
            .build()
        )
      )
      .typecast(true)
      .build();
  }
}

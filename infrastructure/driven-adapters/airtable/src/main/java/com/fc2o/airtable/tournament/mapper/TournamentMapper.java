package com.fc2o.airtable.tournament.mapper;

import com.fc2o.airtable.tournament.dto.*;
import com.fc2o.model.tournament.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TournamentMapper {

  public Tournament toTournament(RecordDto dto) {
    return Tournament.builder()
      .createdTime(dto.createdTime())
      .id(dto.id())
      .name(dto.fields().name())
      .isPaid(dto.fields().isPaid())
      .status(Status.valueOf(dto.fields().status().name()))
      .placeLimit(dto.fields().placeLimit())
      .placeRemaining(dto.fields().placeRemaining())
      .placeMinimum(dto.fields().placeMinimum())
      .promoterId(dto.fields().promoterId())
      .ruleset(dto.fields().ruleset())
      .gameId(dto.fields().gameId())
      .prizePerPosition(dto.fields().prizePerPosition())
      .dateStart(dto.fields().dateStart())
      .dateEnd(dto.fields().dateEnd())
      .transmissions(
        dto.fields().transmissions().stream()
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
          .DonationPercentage(dto.fields().commissionDonationPercentage())
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
      .preRegisteredParticipantIds(dto.fields().preRegisteredParticipantIds())
      .participantIds(dto.fields().participantIds())
      .disqualifiedParticipantIds(dto.fields().disqualifiedParticipantIds())
      .moderatorIds(dto.fields().moderatorIds())
      .closedTournamentJustification(dto.fields().closedTournamentJustification())
      .build();
  }

  public WrapperDto toWrapperDto(Tournament tournament) {
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
                .prizePerPosition(tournament.prizePerPosition())
                .dateStart(tournament.dateStart())
                .dateEnd(tournament.dateEnd())
                .transmissions(
                  tournament.transmissions().stream()
                    .map(transmission ->
                      TransmissionDto.builder()
                        .url(transmission.url())
                        .platform(PlatformDto.valueOf(transmission.platform().name()))
                        .build()
                    )
                    .toList()
                )
                .commissionDonationPercentage(tournament.commission().DonationPercentage())
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
      .build();
  }
}

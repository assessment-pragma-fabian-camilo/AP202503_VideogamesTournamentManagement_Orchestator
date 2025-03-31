package com.fc2o.airtable.tournament.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.math.BigDecimal;

import java.util.Set;

@Builder
public record FieldsDto(
  String name,
  String ruleset,
  String promoterId,
  String gameId,
  Boolean isPaid,
  StatusDto status,
  Short placeLimit,
  Short placeRemaining,
  Short placeMinimum,
  String dateStart,
  String dateEnd,
  String prizePerPosition,
  String transmissions,
  Set<String> preRegisteredParticipantIds,
  Set<String> participantIds,
  Set<String> disqualifiedParticipantIds,
  Set<String> moderatorIds,
  String closedTournamentJustification,
  @JsonProperty("commission.participationPercentage")
  Float commissionParticipationPercentage,
  @JsonProperty("commission.donationPercentage")
  Float commissionDonationPercentage,
  @JsonProperty("commission.visualizationPercentage")
  Float commissionVisualizationPercentage,
  @JsonProperty("price.participation")
  BigDecimal priceParticipation,
  @JsonProperty("price.visualization")
  BigDecimal priceVisualization
) {
}

package com.fc2o.model.tournament;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Builder(toBuilder = true)
public record Tournament(
  UUID id,
  String name,
  String ruleset,
  UUID promoterId,
  UUID gameId,
  Boolean isPaid,
  Status status,
  Short placeLimit,
  Short placeRemaining,
  Short placeMinimum,
  LocalDate dateStart,
  LocalDate dateEnd,
  LocalDateTime createdTime,
  List<Transmission> transmissions,
  Commission commission,
  Set<UUID> moderatorIds,
  Price price,
  String closedTournamentJustification
) {
}

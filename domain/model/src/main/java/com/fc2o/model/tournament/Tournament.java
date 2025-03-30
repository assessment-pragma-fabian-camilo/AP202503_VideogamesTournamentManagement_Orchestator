package com.fc2o.model.tournament;
import lombok.Builder;
import lombok.Singular;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Builder(toBuilder = true)
public record Tournament(
  String id,
  String name,
  String ruleset,
  String promoterId,
  String gameId,
  Map<Byte, BigDecimal> prizePerPosition,
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
  @Singular
  Set<String> preRegisteredParticipantIds,
  @Singular
  Set<String> participantIds,
  @Singular
  Set<String> disqualifiedParticipantIds,
  @Singular
  Set<String> moderatorIds,
  Price price,
  String closedTournamentJustification
) {
  public Boolean isNotStarted() {
    return status().equals(Status.NOT_STARTED);
  }

  public Boolean isInProgress() {
    return status().equals(Status.IN_PROGRESS);
  }

  public Boolean isFinished() {
    return status().equals(Status.FINISHED);
  }

  public Boolean isCanceled() {
    return status().equals(Status.CANCELED);
  }

  public Boolean isFree() {
    return !isPaid();
  }
}

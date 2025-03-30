package com.fc2o.api.dto.tournament.req.register;

import com.fc2o.api.dto.tournament.common.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@Builder
public record TournamentDto(
  String name,
  String ruleset,
  String gameId,
  Map<Byte, BigDecimal> prizePerPosition,
  Boolean isPaid,
  Short placeLimit,
  Short placeMinimum,
  LocalDate dateStart,
  LocalDate dateEnd,
  List<TransmissionDto> transmissions,
  CommissionDto commission,
  String promoterId, //TODO: Eliminar
  PriceDto price
) {
}

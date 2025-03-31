package com.fc2o.api.dto.tournament.res.pre_register_participant;

import lombok.Builder;

@Builder
public record PreRegisterParticipantResponseDto(
  TransactionDto transaction
) {
}

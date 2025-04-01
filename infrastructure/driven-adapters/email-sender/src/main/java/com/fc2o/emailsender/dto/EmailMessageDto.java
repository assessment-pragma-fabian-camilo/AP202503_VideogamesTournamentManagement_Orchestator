package com.fc2o.emailsender.dto;

import lombok.Builder;

@Builder
public record EmailMessageDto(
  String to,
  String subject,
  String body
) {
}

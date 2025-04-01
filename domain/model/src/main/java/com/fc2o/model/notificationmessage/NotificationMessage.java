package com.fc2o.model.notificationmessage;

import lombok.Builder;

@Builder
public record NotificationMessage(
  String to,
  String subject,
  String body
) {
}

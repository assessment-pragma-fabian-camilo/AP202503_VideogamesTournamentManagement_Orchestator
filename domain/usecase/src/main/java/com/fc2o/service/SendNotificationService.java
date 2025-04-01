package com.fc2o.service;

import com.fc2o.model.notificationmessage.NotificationMessage;
import com.fc2o.model.notificationmessage.gateways.NotificationMessageRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SendNotificationService {
  private final NotificationMessageRepository notificationMessageRepository;

  public void send(NotificationMessage message) {
    notificationMessageRepository.send(message);
  }
}

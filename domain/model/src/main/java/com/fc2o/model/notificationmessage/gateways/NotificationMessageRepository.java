package com.fc2o.model.notificationmessage.gateways;

import com.fc2o.model.notificationmessage.NotificationMessage;

public interface NotificationMessageRepository {
  public void send(NotificationMessage message);
}

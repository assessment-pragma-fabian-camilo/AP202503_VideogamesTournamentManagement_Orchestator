package com.fc2o.model.notificationmessage.gateways;

import com.fc2o.model.notificationmessage.NotificationMessage;
import reactor.core.publisher.Mono;

public interface SmsNotificationRepository {
    Mono<Void> send(NotificationMessage message);
}


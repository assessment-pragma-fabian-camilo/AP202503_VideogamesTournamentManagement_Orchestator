package com.fc2o.smsnotification;

import com.fc2o.model.notificationmessage.NotificationMessage;
import com.fc2o.model.notificationmessage.gateways.SmsNotificationRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class SmsNotificationAdapter implements SmsNotificationRepository {

    private final RabbitTemplate rabbitTemplate;
    private final String queueName;

    public SmsNotificationAdapter(
            RabbitTemplate rabbitTemplate,
            @Value("${rabbitmq.queues.sms:notifications.sms}") String queueName
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.queueName = queueName;
    }

    @Override
    public Mono<Void> send(NotificationMessage message) {
        return Mono.fromRunnable(() -> rabbitTemplate.convertAndSend(queueName, message));
    }
}


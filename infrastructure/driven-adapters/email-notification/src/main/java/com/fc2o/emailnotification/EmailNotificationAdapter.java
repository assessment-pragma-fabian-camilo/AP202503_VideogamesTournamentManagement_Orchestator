package com.fc2o.emailnotification;

import com.fc2o.model.notificationmessage.NotificationMessage;
import com.fc2o.model.notificationmessage.gateways.EmailNotificationRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class EmailNotificationAdapter implements EmailNotificationRepository {

    private final RabbitTemplate rabbitTemplate;
    private final String queueName;

    public EmailNotificationAdapter(
            RabbitTemplate rabbitTemplate,
            @Value("${rabbitmq.queues.email:notifications.email}") String queueName
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.queueName = queueName;
    }

    @Override
    public Mono<Void> send(NotificationMessage message) {
        return Mono.fromRunnable(() -> rabbitTemplate.convertAndSend(queueName, message));
    }
}


package com.fc2o.emailsender;

import com.fc2o.emailsender.mapper.EmailMessageMapper;
import com.fc2o.model.notificationmessage.NotificationMessage;
import com.fc2o.model.notificationmessage.gateways.NotificationMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSenderHandler implements NotificationMessageRepository {

  private final EmailSenderService emailSenderService;
  private final EmailMessageMapper emailMessageMapper;

  @Override
  public void send(NotificationMessage message) {
    emailSenderService.sendEmail(emailMessageMapper.toEmailMessageDto(message));
  }
}

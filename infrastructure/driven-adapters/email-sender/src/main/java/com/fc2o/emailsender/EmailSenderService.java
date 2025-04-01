package com.fc2o.emailsender;

import com.fc2o.emailsender.dto.EmailMessageDto;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSenderService {

  private final JavaMailSender mailSender;

  public void sendEmail(EmailMessageDto dto) {
    try {
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setTo(dto.to());
      helper.setSubject(dto.subject());
      helper.setText(dto.body(), true);

      mailSender.send(message);
    }
    catch (Exception e) {
      throw new RuntimeException("Error enviando el correo", e);
    }
  }
}

package com.fc2o.qr;

import com.fc2o.model.ticket.gateways.QrRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class QrHandler implements QrRepository {

  private final QrWebClient qrWebClient;

  @Override
  public Mono<String> retrieve(String ticketId) {
    return qrWebClient.retrieve(ticketId);
  }
}

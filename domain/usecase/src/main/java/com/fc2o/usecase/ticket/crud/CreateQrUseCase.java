package com.fc2o.usecase.ticket.crud;

import com.fc2o.model.ticket.gateways.QrRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateQrUseCase {

  private final QrRepository qrRepository;

  public Mono<String> retrieve(String ticketId) {
    return qrRepository.retrieve(ticketId);
  }

}

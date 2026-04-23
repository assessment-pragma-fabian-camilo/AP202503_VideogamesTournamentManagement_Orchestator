package com.fc2o.model.ticket.gateways;

import reactor.core.publisher.Mono;

public interface QrRepository {
  public Mono<String> retrieve(String ticketId);
}

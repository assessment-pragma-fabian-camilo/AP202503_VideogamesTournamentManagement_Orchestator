package com.fc2o.model.qr.gateways;

import reactor.core.publisher.Mono;

public interface QrRepository {
    Mono<String> retrieve(String ticketId);
}

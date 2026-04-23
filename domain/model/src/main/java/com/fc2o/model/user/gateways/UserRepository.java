package com.fc2o.model.user.gateways;

import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<Boolean> isActiveUser(String id);
}

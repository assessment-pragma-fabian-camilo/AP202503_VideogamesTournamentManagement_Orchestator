package com.fc2o.model.game;

import lombok.Builder;

@Builder(toBuilder = true)
public record Game(
        String id,
        String createdAt,
        String name
) {
}

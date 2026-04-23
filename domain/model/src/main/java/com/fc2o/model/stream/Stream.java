package com.fc2o.model.stream;

import lombok.Builder;

@Builder(toBuilder = true)
public record Stream(
        String id,
        String createdAt,
        String tournamentId,
        StreamPlatform platform,
        String url,
        StreamType type
) {

    public boolean isFree() {
        return type == StreamType.FREE;
    }

    public boolean isPaid() {
        return type == StreamType.PAID;
    }
}


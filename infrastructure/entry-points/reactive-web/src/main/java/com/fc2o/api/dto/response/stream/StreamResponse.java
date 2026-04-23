package com.fc2o.api.dto.response.stream;

import com.fc2o.model.stream.StreamPlatform;
import com.fc2o.model.stream.StreamType;

public record StreamResponse(
        String id,
        String createdAt,
        String tournamentId,
        StreamPlatform platform,
        String url,
        StreamType type
) {
}


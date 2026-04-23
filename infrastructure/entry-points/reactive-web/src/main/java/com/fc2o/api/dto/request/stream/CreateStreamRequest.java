package com.fc2o.api.dto.request.stream;

import com.fc2o.model.stream.StreamPlatform;
import com.fc2o.model.stream.StreamType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public record CreateStreamRequest(
        @NotBlank(message = "Tournament id is required")
        String tournamentId,

        @NotNull(message = "Platform is required")
        StreamPlatform platform,

        @NotBlank(message = "URL is required")
        @URL(message = "URL must be valid")
        String url,

        @NotNull(message = "Type is required")
        StreamType type
) {
}


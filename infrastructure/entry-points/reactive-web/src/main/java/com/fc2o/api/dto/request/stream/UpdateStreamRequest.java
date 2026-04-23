package com.fc2o.api.dto.request.stream;

import com.fc2o.model.stream.StreamType;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public record UpdateStreamRequest(
        @URL(message = "URL must be valid")
        String url,

        @NotNull(message = "Type is required")
        StreamType type
) {
}


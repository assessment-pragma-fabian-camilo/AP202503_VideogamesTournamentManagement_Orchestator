package com.fc2o.api.dto.request.match;

import com.fc2o.model.shared.MatchStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateMatchStatusRequest(
        @NotNull(message = "Status is required")
        MatchStatus status
) {
}


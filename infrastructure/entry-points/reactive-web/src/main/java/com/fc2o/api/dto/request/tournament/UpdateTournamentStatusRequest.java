package com.fc2o.api.dto.request.tournament;

import com.fc2o.model.shared.TournamentStatus;
import jakarta.validation.constraints.NotNull;

/**
 * Request DTO for updating tournament status.
 */
public record UpdateTournamentStatusRequest(
        @NotNull(message = "Status is required")
        TournamentStatus status
) {}


package com.fc2o.api.dto.response.tournament;

import com.fc2o.model.shared.CommissionType;
import com.fc2o.model.shared.TournamentStatus;

public record TournamentResponse(
        String id,
        String createdAt,
        String name,
        String rules,
        String promoterUserId,
        String gameId,
        Short placeLimit,
        Short placeRemaining,
        Short placeMinimum,
        String dateStart,
        String dateEnd,
        Double participationPrice,
        Double visualizationPrice,
        CommissionType type,
        TournamentStatus status,
        String description
) {
}


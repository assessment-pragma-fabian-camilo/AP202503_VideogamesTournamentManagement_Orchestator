package com.fc2o.model.tournament;

import com.fc2o.model.shared.CommissionType;
import com.fc2o.model.shared.TournamentStatus;
import lombok.Builder;

@Builder(toBuilder = true)
public record Tournament(
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

    public Boolean isPaid() {
        return type == CommissionType.PAID;
    }

    public Boolean isFree() {
        return type == CommissionType.FREE;
    }

    public Boolean isUpcoming() {
        return status == TournamentStatus.UPCOMING;
    }

    public Boolean isOngoing() {
        return status == TournamentStatus.ONGOING;
    }

    public Boolean isCompleted() {
        return status == TournamentStatus.COMPLETED;
    }

    public Boolean isCanceled() {
        return status == TournamentStatus.CANCELED;
    }
}

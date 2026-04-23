package com.fc2o.model.match;

import com.fc2o.model.shared.MatchStatus;
import lombok.Builder;

import java.util.Set;

@Builder(toBuilder = true)
public record Match(
        String id,
        String createdAt,
        String startDateTime,
        String endDateTime,
        String tournamentId,
        String winnerTeamId,
        MatchStatus status,
        String matchDetails,
        Set<String> participantIds
) {

    public Boolean isScheduled() {
        return status == MatchStatus.SCHEDULED;
    }

    public Boolean isOngoing() {
        return status == MatchStatus.ONGOING;
    }

    public Boolean isCompleted() {
        return status == MatchStatus.COMPLETED;
    }

    public Boolean isCanceled() {
        return status == MatchStatus.CANCELED;
    }
}

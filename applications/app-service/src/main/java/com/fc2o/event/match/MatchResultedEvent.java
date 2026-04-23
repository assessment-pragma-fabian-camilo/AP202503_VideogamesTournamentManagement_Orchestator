package com.fc2o.event.match;

import java.time.Instant;
import java.util.Set;

/**
 * Event published when match results are defined.
 */
public record MatchResultedEvent(
    String matchId,
    String winnerTeamId,
    Set<String> participantIds,
    Instant timestamp
) {}


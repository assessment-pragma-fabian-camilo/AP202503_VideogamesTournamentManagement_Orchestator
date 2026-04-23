package com.fc2o.event.tournament;

import java.time.Instant;

/**
 * Event published when tournament status changes.
 */
public record TournamentStatusChangedEvent(
    String tournamentId,
    String newStatus,
    String promoterId,
    Instant timestamp
) {}


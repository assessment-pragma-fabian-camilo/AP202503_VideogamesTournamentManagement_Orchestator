package com.fc2o.usecase.fixture;

import com.fc2o.model.tournament.Tournament;

import java.time.LocalDateTime;

/**
 * Fixture for creating mock Tournament objects in tests.
 */
public class TournamentFixture {

    public static Tournament mockTournament() {
        return Tournament.builder()
            .id("tournament-123")
            .name("Test Tournament")
            .status(Status.UPCOMING)
            .promoterUserId("promoter-123")
            .gameId("game-123")
            .placeLimit((short) 100)
            .placeRemaining((short) 100)
            .placeMinimum((short) 10)
            .dateStart("2026-05-01")
            .dateEnd("2026-05-31")
            .isPaid(true)
            .createdTime(LocalDateTime.now().toString())
            .build();
    }

    public static Tournament mockTournamentOngoing() {
        return mockTournament().toBuilder()
            .status(Status.ONGOING)
            .build();
    }

    public static Tournament mockTournamentCompleted() {
        return mockTournament().toBuilder()
            .status(Status.COMPLETED)
            .build();
    }
}


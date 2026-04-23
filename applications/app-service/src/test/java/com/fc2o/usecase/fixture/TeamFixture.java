package com.fc2o.usecase.fixture;

import com.fc2o.model.team.Team;

/**
 * Fixture for creating mock Team objects in tests.
 */
public class TeamFixture {

    public static Team mockTeam() {
        return Team.builder()
            .id("team-123")
            .name("Test Team")
            .leaderUserId("leader-123")
            .createdAt("2026-04-01")
            .build();
    }

    public static Team mockTeamWithName(String name) {
        return mockTeam().toBuilder()
            .name(name)
            .build();
    }
}


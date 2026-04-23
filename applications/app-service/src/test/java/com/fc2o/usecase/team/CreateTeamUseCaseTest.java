package com.fc2o.usecase.team;

import com.fc2o.model.team.Team;
import com.fc2o.model.team.gateways.TeamRepository;
import com.fc2o.model.team.gateways.TeamMemberRepository;
import com.fc2o.model.user.gateways.UserRepository;
import com.fc2o.usecase.fixture.TeamFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit tests for CreateTeamUseCase.
 */
@ExtendWith(MockitoExtension.class)
public class CreateTeamUseCaseTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private TeamMemberRepository teamMemberRepository;

    @Mock
    private UserRepository userRepository;

    private CreateTeamUseCase useCase;

    @BeforeEach
    public void setUp() {
        useCase = new CreateTeamUseCase(teamRepository, teamMemberRepository, userRepository);
    }

    @Test
    public void testCreateTeamSuccess() {
        // Arrange
        Team mockTeam = TeamFixture.mockTeam();
        User mockUser = User.builder().id("leader-123").build();

        when(userRepository.findById("leader-123")).thenReturn(Mono.just(mockUser));
        when(teamRepository.save(any(Team.class))).thenReturn(Mono.just(mockTeam));
        when(teamMemberRepository.save(any())).thenReturn(Mono.just(null));

        // Act & Assert
        StepVerifier.create(useCase.execute("Test Team", "leader-123"))
            .assertNext(team -> {
                assertEquals("Test Team", team.name());
                assertEquals("leader-123", team.leaderUserId());
            })
            .verifyComplete();
    }

    @Test
    public void testCreateTeamWithInvalidName() {
        // Act & Assert
        StepVerifier.create(useCase.execute(null, "leader-123"))
            .expectError(IllegalArgumentException.class)
            .verify();
    }

    @Test
    public void testCreateTeamLeaderNotFound() {
        // Arrange
        when(userRepository.findById("invalid-leader")).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(useCase.execute("Test Team", "invalid-leader"))
            .expectError(IllegalArgumentException.class)
            .verify();
    }
}


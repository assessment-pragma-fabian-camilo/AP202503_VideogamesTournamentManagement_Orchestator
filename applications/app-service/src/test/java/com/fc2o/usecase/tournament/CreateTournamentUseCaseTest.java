package com.fc2o.usecase.tournament;

import com.fc2o.model.tournament.Tournament;
import com.fc2o.model.tournament.gateways.TournamentRepository;
import com.fc2o.model.game.gateways.GameRepository;
import com.fc2o.model.user.gateways.UserRepository;
import com.fc2o.model.user.User;
import com.fc2o.model.game.Game;
import com.fc2o.usecase.fixture.TournamentFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Unit tests for CreateTournamentUseCase.
 */
@ExtendWith(MockitoExtension.class)
public class CreateTournamentUseCaseTest {

    @Mock
    private TournamentRepository tournamentRepository;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private UserRepository userRepository;

    private CreateTournamentUseCase useCase;

    @BeforeEach
    public void setUp() {
        useCase = new CreateTournamentUseCase(tournamentRepository, gameRepository, userRepository);
    }

    @Test
    public void testCreateTournamentSuccess() {
        // Arrange
        Tournament mockTournament = TournamentFixture.mockTournament();
        User mockUser = User.builder().id("promoter-123").build();
        Game mockGame = Game.builder().id("game-123").name("League of Legends").build();

        when(userRepository.findById("promoter-123")).thenReturn(Mono.just(mockUser));
        when(gameRepository.findById("game-123")).thenReturn(Mono.just(mockGame));
        when(tournamentRepository.save(any(Tournament.class))).thenReturn(Mono.just(mockTournament));

        // Act & Assert
        StepVerifier.create(useCase.execute(
            "Test Tournament",
            "promoter-123",
            "game-123",
            (short) 100,
            (short) 10,
            "2026-05-01",
            "2026-05-31",
            100.0,
            50.0,
            true,
            "Tournament rules",
            "Tournament description"
        ))
            .assertNext(tournament -> {
                assertEquals("Test Tournament", tournament.name());
                assertEquals("UPCOMING", tournament.status().name());
                assertEquals((short) 100, tournament.placeLimit());
            })
            .verifyComplete();
    }

    @Test
    public void testCreateTournamentWithInvalidName() {
        // Act & Assert
        StepVerifier.create(useCase.execute(
            null,
            "promoter-123",
            "game-123",
            (short) 100,
            (short) 10,
            "2026-05-01",
            "2026-05-31",
            100.0,
            50.0,
            true,
            "Rules",
            "Description"
        ))
            .expectError(IllegalArgumentException.class)
            .verify();
    }

    @Test
    public void testCreateTournamentWithInvalidPlaceLimits() {
        // Act & Assert - place_limit < place_minimum
        StepVerifier.create(useCase.execute(
            "Test Tournament",
            "promoter-123",
            "game-123",
            (short) 5,    // less than place_minimum
            (short) 10,   // minimum
            "2026-05-01",
            "2026-05-31",
            100.0,
            50.0,
            true,
            "Rules",
            "Description"
        ))
            .expectError(IllegalArgumentException.class)
            .verify();
    }

    @Test
    public void testCreateTournamentPromoterNotFound() {
        // Arrange
        when(userRepository.findById("invalid-promoter")).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(useCase.execute(
            "Test Tournament",
            "invalid-promoter",
            "game-123",
            (short) 100,
            (short) 10,
            "2026-05-01",
            "2026-05-31",
            100.0,
            50.0,
            true,
            "Rules",
            "Description"
        ))
            .expectError(IllegalArgumentException.class)
            .verify();
    }
}


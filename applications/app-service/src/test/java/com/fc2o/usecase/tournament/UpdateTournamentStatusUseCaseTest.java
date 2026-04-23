package com.fc2o.usecase.tournament;

import com.fc2o.model.tournament.Tournament;
import com.fc2o.model.tournament.gateways.TournamentRepository;
import com.fc2o.usecase.fixture.TournamentFixture;
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
 * Unit tests for UpdateTournamentStatusUseCase.
 */
@ExtendWith(MockitoExtension.class)
public class UpdateTournamentStatusUseCaseTest {

    @Mock
    private TournamentRepository tournamentRepository;

    private UpdateTournamentStatusUseCase useCase;

    @BeforeEach
    public void setUp() {
        useCase = new UpdateTournamentStatusUseCase(tournamentRepository);
    }

    @Test
    public void testUpdateTournamentStatusToOngoing() {
        // Arrange
        Tournament mockTournament = TournamentFixture.mockTournament();
        Tournament updatedTournament = mockTournament.toBuilder()
            .status(Status.ONGOING)
            .build();

        when(tournamentRepository.findById("tournament-123")).thenReturn(Mono.just(mockTournament));
        when(tournamentRepository.update(any(Tournament.class))).thenReturn(Mono.just(updatedTournament));

        // Act & Assert
        StepVerifier.create(useCase.execute("tournament-123", Status.ONGOING))
            .assertNext(tournament -> assertEquals(Status.ONGOING, tournament.status()))
            .verifyComplete();
    }

    @Test
    public void testUpdateTournamentStatusInvalidTransition() {
        // Arrange
        Tournament mockTournament = TournamentFixture.mockTournamentCompleted();

        when(tournamentRepository.findById("tournament-123")).thenReturn(Mono.just(mockTournament));

        // Act & Assert - Cannot transition from COMPLETED to ONGOING
        StepVerifier.create(useCase.execute("tournament-123", Status.ONGOING))
            .expectError(IllegalArgumentException.class)
            .verify();
    }

    @Test
    public void testUpdateTournamentStatusNotFound() {
        // Arrange
        when(tournamentRepository.findById("invalid-id")).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(useCase.execute("invalid-id", Status.ONGOING))
            .expectError(IllegalArgumentException.class)
            .verify();
    }
}


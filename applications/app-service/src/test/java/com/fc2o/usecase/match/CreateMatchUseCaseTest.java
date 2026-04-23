package com.fc2o.usecase.match;

import com.fc2o.model.match.Match;
import com.fc2o.model.match.gateways.MatchRepository;
import com.fc2o.model.tournament.gateways.TournamentRepository;
import com.fc2o.model.tournament.Tournament;
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
 * Unit tests for CreateMatchUseCase.
 */
@ExtendWith(MockitoExtension.class)
public class CreateMatchUseCaseTest {

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private TournamentRepository tournamentRepository;

    private CreateMatchUseCase useCase;

    @BeforeEach
    public void setUp() {
        useCase = new CreateMatchUseCase(matchRepository, tournamentRepository);
    }

    @Test
    public void testCreateMatchSuccess() {
        // Arrange
        Tournament mockTournament = TournamentFixture.mockTournamentOngoing();
        Match mockMatch = Match.builder()
            .id("match-123")
            .tournamentId("tournament-123")
            .dateStart("2026-05-15")
            .status(Status.SCHEDULED)
            .build();

        when(tournamentRepository.findById("tournament-123")).thenReturn(Mono.just(mockTournament));
        when(matchRepository.save(any(Match.class))).thenReturn(Mono.just(mockMatch));

        // Act & Assert
        StepVerifier.create(useCase.execute("tournament-123", "2026-05-15", "18:00"))
            .assertNext(match -> {
                assertEquals("match-123", match.id());
                assertEquals(Status.SCHEDULED, match.status());
            })
            .verifyComplete();
    }

    @Test
    public void testCreateMatchTournamentNotOngoing() {
        // Arrange
        Tournament mockTournament = TournamentFixture.mockTournament(); // UPCOMING status

        when(tournamentRepository.findById("tournament-123")).thenReturn(Mono.just(mockTournament));

        // Act & Assert
        StepVerifier.create(useCase.execute("tournament-123", "2026-05-15", "18:00"))
            .expectError(IllegalArgumentException.class)
            .verify();
    }
}


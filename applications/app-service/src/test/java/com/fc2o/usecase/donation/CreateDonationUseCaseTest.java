package com.fc2o.usecase.donation;

import com.fc2o.model.donation.Donation;
import com.fc2o.model.donation.gateways.DonationRepository;
import com.fc2o.model.tournament.gateways.TournamentRepository;
import com.fc2o.model.user.gateways.UserRepository;
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
 * Unit tests for CreateDonationUseCase.
 */
@ExtendWith(MockitoExtension.class)
public class CreateDonationUseCaseTest {

    @Mock
    private DonationRepository donationRepository;

    @Mock
    private TournamentRepository tournamentRepository;

    @Mock
    private UserRepository userRepository;

    private CreateDonationUseCase useCase;

    @BeforeEach
    public void setUp() {
        useCase = new CreateDonationUseCase(donationRepository, tournamentRepository, userRepository);
    }

    @Test
    public void testCreateDonationSuccess() {
        // Arrange
        Tournament mockTournament = TournamentFixture.mockTournamentOngoing();
        User mockUser = User.builder().id("user-123").build();
        Donation mockDonation = Donation.builder()
            .id("donation-123")
            .tournamentId("tournament-123")
            .userId("user-123")
            .amount(100.0)
            .build();

        when(userRepository.findById("user-123")).thenReturn(Mono.just(mockUser));
        when(tournamentRepository.findById("tournament-123")).thenReturn(Mono.just(mockTournament));
        when(donationRepository.save(any(Donation.class))).thenReturn(Mono.just(mockDonation));

        // Act & Assert
        StepVerifier.create(useCase.execute("tournament-123", "user-123", null, 100.0, "Thank you"))
            .assertNext(donation -> {
                assertEquals("donation-123", donation.id());
                assertEquals(100.0, donation.amount());
            })
            .verifyComplete();
    }

    @Test
    public void testCreateDonationTournamentNotOngoing() {
        // Arrange
        Tournament mockTournament = TournamentFixture.mockTournament(); // UPCOMING status
        User mockUser = User.builder().id("user-123").build();

        when(userRepository.findById("user-123")).thenReturn(Mono.just(mockUser));
        when(tournamentRepository.findById("tournament-123")).thenReturn(Mono.just(mockTournament));

        // Act & Assert
        StepVerifier.create(useCase.execute("tournament-123", "user-123", null, 100.0, "Thank you"))
            .expectError(IllegalArgumentException.class)
            .verify();
    }

    @Test
    public void testCreateDonationInvalidAmount() {
        // Act & Assert
        StepVerifier.create(useCase.execute("tournament-123", "user-123", null, -50.0, "Thank you"))
            .expectError(IllegalArgumentException.class)
            .verify();
    }
}


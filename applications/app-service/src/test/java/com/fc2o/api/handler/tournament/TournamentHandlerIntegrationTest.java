package com.fc2o.api.handler.tournament;

import com.fc2o.api.dto.request.tournament.CreateTournamentRequest;
import com.fc2o.api.handler.TournamentHandler;
import com.fc2o.api.mapper.tournament.TournamentMapper;
import com.fc2o.model.tournament.Tournament;
import com.fc2o.usecase.fixture.TournamentFixture;
import com.fc2o.usecase.tournament.CreateTournamentUseCase;
import com.fc2o.usecase.tournament.GetTournamentUseCase;
import com.fc2o.usecase.tournament.ListTournamentsUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.function.server.RouterFunctions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Integration tests for Tournament endpoints.
 */
@ExtendWith(MockitoExtension.class)
public class TournamentHandlerIntegrationTest {

    private WebTestClient webTestClient;

    @Mock
    private CreateTournamentUseCase createTournamentUseCase;

    @Mock
    private GetTournamentUseCase getTournamentUseCase;

    @Mock
    private ListTournamentsUseCase listTournamentsUseCase;

    @Mock
    private TournamentMapper tournamentMapper;

    private TournamentHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new TournamentHandler(
            createTournamentUseCase,
            getTournamentUseCase,
            listTournamentsUseCase,
            tournamentMapper
        );

        RouterFunction<ServerResponse> routes = RouterFunctions.route()
            .POST("/api/tournaments", handler::createTournament)
            .GET("/api/tournaments/{id}", handler::getTournament)
            .GET("/api/tournaments", handler::listTournaments)
            .build();

        webTestClient = WebTestClient.bindToRouterFunction(routes).build();
    }

    @Test
    public void testCreateTournamentSuccess() {
        // Arrange
        Tournament mockTournament = TournamentFixture.mockTournament();
        CreateTournamentRequest request = new CreateTournamentRequest(
            "Test Tournament",
            "promoter-123",
            "game-123",
            (short) 100,
            (short) 10,
            "2026-05-01",
            "2026-05-31",
            BigDecimal.valueOf(100.0),
            BigDecimal.valueOf(50.0),
            true,
            "Rules",
            "Description"
        );

        when(createTournamentUseCase.execute(anyString(), anyString(), anyString(), any(), any(), anyString(), anyString(), any(), any(), any(), anyString(), anyString()))
            .thenReturn(Mono.just(mockTournament));
        when(tournamentMapper.toResponse(mockTournament))
            .thenReturn(null); // Response mapping tested separately

        // Act & Assert
        webTestClient.post()
            .uri("/api/tournaments")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(request))
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().exists("Location");
    }

    @Test
    public void testGetTournamentSuccess() {
        // Arrange
        Tournament mockTournament = TournamentFixture.mockTournament();
        when(getTournamentUseCase.execute("tournament-123"))
            .thenReturn(Mono.just(mockTournament));
        when(tournamentMapper.toResponse(mockTournament))
            .thenReturn(null);

        // Act & Assert
        webTestClient.get()
            .uri("/api/tournaments/tournament-123")
            .exchange()
            .expectStatus().isOk();
    }

    @Test
    public void testGetTournamentNotFound() {
        // Arrange
        when(getTournamentUseCase.execute("invalid-id"))
            .thenReturn(Mono.error(new IllegalArgumentException("Tournament not found")));

        // Act & Assert
        webTestClient.get()
            .uri("/api/tournaments/invalid-id")
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    public void testListTournaments() {
        // Arrange
        Tournament mockTournament1 = TournamentFixture.mockTournament();
        Tournament mockTournament2 = TournamentFixture.mockTournamentOngoing();

        when(listTournamentsUseCase.executeAll())
            .thenReturn(Flux.just(mockTournament1, mockTournament2));

        // Act & Assert
        webTestClient.get()
            .uri("/api/tournaments")
            .exchange()
            .expectStatus().isOk();
    }
}


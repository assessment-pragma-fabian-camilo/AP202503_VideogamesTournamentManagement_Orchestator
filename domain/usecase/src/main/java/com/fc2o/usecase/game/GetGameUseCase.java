package com.fc2o.usecase.game;

import com.fc2o.model.game.Game;
import com.fc2o.model.game.gateways.GameRepository;
import reactor.core.publisher.Mono;

/**
 * Use case for retrieving a game by ID.
 * Any user can retrieve game information.
 */
public class GetGameUseCase {
    private final GameRepository gameRepository;

    public GetGameUseCase(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Mono<Game> execute(String gameId) {
        if (gameId == null || gameId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Game ID is required"));
        }
        return gameRepository.findById(gameId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Game not found with ID: " + gameId)));
    }
}


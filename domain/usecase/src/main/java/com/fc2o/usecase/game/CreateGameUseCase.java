package com.fc2o.usecase.game;

import com.fc2o.model.game.Game;
import com.fc2o.model.game.gateways.GameRepository;
import reactor.core.publisher.Mono;

/**
 * Use case for creating a new game.
 * Only users with ADMIN role can create games.
 * Business rule: Games available for tournaments must be defined by administrators.
 */
public class CreateGameUseCase {
    private final GameRepository gameRepository;

    public CreateGameUseCase(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Mono<Game> execute(Game game) {
        if (game == null || game.name() == null || game.name().isBlank()) {
            return Mono.error(new IllegalArgumentException("Game name is required"));
        }
        return gameRepository.save(game);
    }
}


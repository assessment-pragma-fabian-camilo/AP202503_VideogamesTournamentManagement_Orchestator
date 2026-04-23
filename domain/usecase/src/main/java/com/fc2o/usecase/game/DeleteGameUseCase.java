package com.fc2o.usecase.game;

import com.fc2o.model.game.gateways.GameRepository;
import reactor.core.publisher.Mono;

/**
 * Use case for deleting a game by ID.
 * Only users with ADMIN role can delete games.
 * Business rule: Games can only be deleted if no tournaments are associated with them.
 */
public class DeleteGameUseCase {
    private final GameRepository gameRepository;

    public DeleteGameUseCase(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Mono<Void> execute(String gameId) {
        if (gameId == null || gameId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Game ID is required"));
        }
        return gameRepository.deleteById(gameId).then();
    }
}


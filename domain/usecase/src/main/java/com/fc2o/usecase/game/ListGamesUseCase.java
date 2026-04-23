package com.fc2o.usecase.game;

import com.fc2o.model.game.Game;
import com.fc2o.model.game.gateways.GameRepository;
import reactor.core.publisher.Flux;

/**
 * Use case for listing all available games.
 * Any user can list games.
 */
public class ListGamesUseCase {
    private final GameRepository gameRepository;

    public ListGamesUseCase(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Flux<Game> execute() {
        return gameRepository.findAll();
    }
}


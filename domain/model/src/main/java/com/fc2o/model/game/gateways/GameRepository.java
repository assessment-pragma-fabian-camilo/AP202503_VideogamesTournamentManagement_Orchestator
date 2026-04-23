package com.fc2o.model.game.gateways;

import com.fc2o.model.game.Game;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Gateway interface for Game repository operations.
 * Defines all operations needed by game use cases.
 */
public interface GameRepository {
    /**
     * Find game by ID.
     */
    Mono<Game> findById(String id);

    /**
     * Find all games.
     */
    Flux<Game> findAll();

    /**
     * Find game by name.
     */
    Mono<Game> findByName(String name);

    /**
     * Create a new game.
     */
    Mono<Game> save(Game game);

    /**
     * Update an entire game.
     */
    Mono<Game> update(Game game);

    /**
     * Delete game by ID.
     */
    Mono<Game> deleteById(String id);

    Mono<Game> patch(Game game);
}

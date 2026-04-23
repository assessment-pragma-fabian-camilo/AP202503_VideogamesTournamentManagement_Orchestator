package com.fc2o.config.usecaseconfig;

import com.fc2o.model.game.gateways.GameRepository;
import com.fc2o.usecase.game.CreateGameUseCase;
import com.fc2o.usecase.game.DeleteGameUseCase;
import com.fc2o.usecase.game.GetGameUseCase;
import com.fc2o.usecase.game.ListGamesUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Game use cases.
 */
@Configuration
public class GameUseCaseConfig {

    @Bean
    public CreateGameUseCase createGameUseCase(
            GameRepository gameRepository) {
        return new CreateGameUseCase(gameRepository);
    }

    @Bean
    public GetGameUseCase getGameUseCase(
            GameRepository gameRepository) {
        return new GetGameUseCase(gameRepository);
    }

    @Bean
    public ListGamesUseCase listGamesUseCase(
            GameRepository gameRepository) {
        return new ListGamesUseCase(gameRepository);
    }

    @Bean
    public DeleteGameUseCase deleteGameUseCase(
            GameRepository gameRepository) {
        return new DeleteGameUseCase(gameRepository);
    }
}


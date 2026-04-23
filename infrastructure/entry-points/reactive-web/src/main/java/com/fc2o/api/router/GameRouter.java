package com.fc2o.api.router;

import com.fc2o.api.handler.GameHandler;
import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class GameRouter {

    @Bean
    @Order(1)
    @RouterOperations({
            @RouterOperation(path = "/api/v1/games", method = RequestMethod.GET, beanClass = GameHandler.class, beanMethod = "listGames", operation = @Operation(operationId = "listGames", summary = "List games", tags = {"Games"})),
            @RouterOperation(path = "/api/v1/games/{id}", method = RequestMethod.GET, beanClass = GameHandler.class, beanMethod = "getGame", operation = @Operation(operationId = "getGame", summary = "Get game by id", tags = {"Games"})),
            @RouterOperation(path = "/api/v1/games", method = RequestMethod.POST, beanClass = GameHandler.class, beanMethod = "createGame", operation = @Operation(operationId = "createGame", summary = "Create game", tags = {"Games"})),
            @RouterOperation(path = "/api/v1/games/{id}", method = RequestMethod.DELETE, beanClass = GameHandler.class, beanMethod = "deleteGame", operation = @Operation(operationId = "deleteGame", summary = "Delete game", tags = {"Games"}))
    })
    public RouterFunction<ServerResponse> gameRouterFunction(GameHandler gameHandler) {
        return RouterFunctions.route()
                .path("/api/v1/games", builder -> builder
                        .GET("", gameHandler::listGames)
                        .GET("/{id}", gameHandler::getGame)
                        .POST("", gameHandler::createGame)
                        .DELETE("/{id}", gameHandler::deleteGame))
                .build();
    }
}


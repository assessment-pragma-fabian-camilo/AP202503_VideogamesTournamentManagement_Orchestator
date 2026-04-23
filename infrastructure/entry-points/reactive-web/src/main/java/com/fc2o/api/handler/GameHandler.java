package com.fc2o.api.handler;

import com.fc2o.api.dto.request.game.CreateGameRequest;
import com.fc2o.api.dto.response.generic.ApiResponse;
import com.fc2o.api.mapper.game.GameMapper;
import com.fc2o.api.validator.ValidatorHandler;
import com.fc2o.model.game.Game;
import com.fc2o.usecase.game.CreateGameUseCase;
import com.fc2o.usecase.game.DeleteGameUseCase;
import com.fc2o.usecase.game.GetGameUseCase;
import com.fc2o.usecase.game.ListGamesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class GameHandler {

    private final CreateGameUseCase createGameUseCase;
    private final GetGameUseCase getGameUseCase;
    private final ListGamesUseCase listGamesUseCase;
    private final DeleteGameUseCase deleteGameUseCase;
    private final GameMapper gameMapper;
    private final ValidatorHandler validatorHandler;

    @PreAuthorize("isAuthenticated()")
    public Mono<ServerResponse> getGame(ServerRequest request) {
        return getGameUseCase.execute(request.pathVariable("id"))
                .map(gameMapper::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }

    @PreAuthorize("isAuthenticated()")
    public Mono<ServerResponse> listGames(ServerRequest request) {
        return listGamesUseCase.execute()
                .map(gameMapper::toResponse)
                .collectList()
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ServerResponse> createGame(ServerRequest request) {
        return request.bodyToMono(CreateGameRequest.class)
                .doOnNext(validatorHandler::validate)
                .map(body -> Game.builder().name(body.name()).build())
                .flatMap(createGameUseCase::execute)
                .map(gameMapper::toResponse)
                .flatMap(response -> ServerResponse.created(URI.create("/api/v1/games/" + response.id()))
                        .bodyValue(ApiResponse.created(response)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ServerResponse> deleteGame(ServerRequest request) {
        return deleteGameUseCase.execute(request.pathVariable("id"))
                .then(ServerResponse.noContent().build());
    }
}


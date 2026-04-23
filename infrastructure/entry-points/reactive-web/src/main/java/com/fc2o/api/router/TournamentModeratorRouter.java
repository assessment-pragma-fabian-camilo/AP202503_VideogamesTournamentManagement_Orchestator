package com.fc2o.api.router;

import com.fc2o.api.handler.TournamentModeratorHandler;
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
public class TournamentModeratorRouter {

    @Bean
    @Order(1)
    @RouterOperations({
            @RouterOperation(path = "/api/v1/tournament-moderators/tournament/{tournamentId}", method = RequestMethod.GET, beanClass = TournamentModeratorHandler.class, beanMethod = "listModerators", operation = @Operation(operationId = "listModerators", summary = "List tournament moderators", tags = {"Tournament Moderators"})),
            @RouterOperation(path = "/api/v1/tournament-moderators", method = RequestMethod.POST, beanClass = TournamentModeratorHandler.class, beanMethod = "assignModerator", operation = @Operation(operationId = "assignModerator", summary = "Assign tournament moderator", tags = {"Tournament Moderators"})),
            @RouterOperation(path = "/api/v1/tournament-moderators/tournament/{tournamentId}/user/{userId}", method = RequestMethod.DELETE, beanClass = TournamentModeratorHandler.class, beanMethod = "removeModerator", operation = @Operation(operationId = "removeModerator", summary = "Remove tournament moderator", tags = {"Tournament Moderators"}))
    })
    public RouterFunction<ServerResponse> tournamentModeratorRouterFunction(TournamentModeratorHandler handler) {
        return RouterFunctions.route()
                .path("/api/v1/tournament-moderators", builder -> builder
                        .GET("/tournament/{tournamentId}", handler::listModerators)
                        .POST("", handler::assignModerator)
                        .DELETE("/tournament/{tournamentId}/user/{userId}", handler::removeModerator))
                .build();
    }
}


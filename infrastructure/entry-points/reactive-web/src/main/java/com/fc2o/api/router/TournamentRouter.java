package com.fc2o.api.router;

import com.fc2o.api.handler.TournamentHandler;
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
public class TournamentRouter {

    @Bean
    @Order(1)
    @RouterOperations({
            @RouterOperation(path = "/api/v1/tournaments", method = RequestMethod.GET, beanClass = TournamentHandler.class, beanMethod = "listTournaments", operation = @Operation(operationId = "listTournaments", summary = "List tournaments", tags = {"Tournaments"})),
            @RouterOperation(path = "/api/v1/tournaments/promoter/{promoterId}", method = RequestMethod.GET, beanClass = TournamentHandler.class, beanMethod = "listTournamentsByPromoter", operation = @Operation(operationId = "listTournamentsByPromoter", summary = "List tournaments by promoter", tags = {"Tournaments"})),
            @RouterOperation(path = "/api/v1/tournaments/{id}", method = RequestMethod.GET, beanClass = TournamentHandler.class, beanMethod = "getTournament", operation = @Operation(operationId = "getTournament", summary = "Get tournament by id", tags = {"Tournaments"})),
            @RouterOperation(path = "/api/v1/tournaments", method = RequestMethod.POST, beanClass = TournamentHandler.class, beanMethod = "createTournament", operation = @Operation(operationId = "createTournament", summary = "Create tournament", tags = {"Tournaments"})),
            @RouterOperation(path = "/api/v1/tournaments/{id}", method = RequestMethod.PUT, beanClass = TournamentHandler.class, beanMethod = "updateTournament", operation = @Operation(operationId = "updateTournament", summary = "Update tournament", tags = {"Tournaments"})),
            @RouterOperation(path = "/api/v1/tournaments/{id}/cancel", method = RequestMethod.PATCH, beanClass = TournamentHandler.class, beanMethod = "cancelTournament", operation = @Operation(operationId = "cancelTournament", summary = "Cancel tournament", tags = {"Tournaments"})),
            @RouterOperation(path = "/api/v1/tournaments/{id}/complete", method = RequestMethod.PATCH, beanClass = TournamentHandler.class, beanMethod = "completeTournament", operation = @Operation(operationId = "completeTournament", summary = "Complete tournament", tags = {"Tournaments"})),
            @RouterOperation(path = "/api/v1/tournaments/{id}/place-limits", method = RequestMethod.PATCH, beanClass = TournamentHandler.class, beanMethod = "updatePlaceLimits", operation = @Operation(operationId = "updatePlaceLimits", summary = "Update place limits", tags = {"Tournaments"})),
            @RouterOperation(path = "/api/v1/tournaments/{id}/rules", method = RequestMethod.PATCH, beanClass = TournamentHandler.class, beanMethod = "updateTournamentRules", operation = @Operation(operationId = "updateTournamentRules", summary = "Update tournament rules", tags = {"Tournaments"})),
            @RouterOperation(path = "/api/v1/tournaments/{id}/status", method = RequestMethod.PATCH, beanClass = TournamentHandler.class, beanMethod = "updateTournamentStatus", operation = @Operation(operationId = "updateTournamentStatus", summary = "Update tournament status", tags = {"Tournaments"}))
    })
    public RouterFunction<ServerResponse> tournamentRouterFunction(TournamentHandler tournamentHandler) {
        return RouterFunctions
                .route()
                .path("/api/v1/tournaments",
                        builder -> builder
                                // READ
                                .GET("", tournamentHandler::listTournaments)
                                .GET("/promoter/{promoterId}", tournamentHandler::listTournamentsByPromoter)
                                .GET("/{id}", tournamentHandler::getTournament)
                                // CREATE
                                .POST("", tournamentHandler::createTournament)
                                // UPDATE
                                .PUT("/{id}", tournamentHandler::updateTournament)
                                .PATCH("/{id}/cancel", tournamentHandler::cancelTournament)
                                .PATCH("/{id}/complete", tournamentHandler::completeTournament)
                                .PATCH("/{id}/place-limits", tournamentHandler::updatePlaceLimits)
                                .PATCH("/{id}/rules", tournamentHandler::updateTournamentRules)
                                .PATCH("/{id}/status", tournamentHandler::updateTournamentStatus)
                )
                .build();
    }
}

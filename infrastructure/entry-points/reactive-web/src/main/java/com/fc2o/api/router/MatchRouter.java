package com.fc2o.api.router;

import com.fc2o.api.handler.MatchHandler;
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
public class MatchRouter {
    @Bean
    @Order(1)
    @RouterOperations({
            @RouterOperation(path = "/api/v1/matches", method = RequestMethod.POST, beanClass = MatchHandler.class, beanMethod = "createMatch", operation = @Operation(operationId = "createMatch", summary = "Create match", tags = {"Matches"})),
            @RouterOperation(path = "/api/v1/matches/{id}", method = RequestMethod.GET, beanClass = MatchHandler.class, beanMethod = "getMatch", operation = @Operation(operationId = "getMatch", summary = "Get match by id", tags = {"Matches"})),
            @RouterOperation(path = "/api/v1/matches/tournament/{tournamentId}", method = RequestMethod.GET, beanClass = MatchHandler.class, beanMethod = "listMatchesByTournament", operation = @Operation(operationId = "listMatchesByTournament", summary = "List matches by tournament", tags = {"Matches"})),
            @RouterOperation(path = "/api/v1/matches/tournament/{tournamentId}/participant/{participantId}", method = RequestMethod.GET, beanClass = MatchHandler.class, beanMethod = "listMatchesByParticipantAndTournament", operation = @Operation(operationId = "listMatchesByParticipantAndTournament", summary = "List matches by participant and tournament", tags = {"Matches"})),
            @RouterOperation(path = "/api/v1/matches", method = RequestMethod.GET, beanClass = MatchHandler.class, beanMethod = "listMatches", operation = @Operation(operationId = "listMatches", summary = "List matches", tags = {"Matches"})),
            @RouterOperation(path = "/api/v1/matches/{id}", method = RequestMethod.PUT, beanClass = MatchHandler.class, beanMethod = "updateMatch", operation = @Operation(operationId = "updateMatch", summary = "Update match", tags = {"Matches"})),
            @RouterOperation(path = "/api/v1/matches/{id}/status", method = RequestMethod.PATCH, beanClass = MatchHandler.class, beanMethod = "updateMatchStatus", operation = @Operation(operationId = "updateMatchStatus", summary = "Update match status", tags = {"Matches"})),
            @RouterOperation(path = "/api/v1/matches/{id}/details", method = RequestMethod.PATCH, beanClass = MatchHandler.class, beanMethod = "updateMatchDetails", operation = @Operation(operationId = "updateMatchDetails", summary = "Update match details", tags = {"Matches"})),
            @RouterOperation(path = "/api/v1/matches/{id}/cancel", method = RequestMethod.PATCH, beanClass = MatchHandler.class, beanMethod = "cancelMatch", operation = @Operation(operationId = "cancelMatch", summary = "Cancel match", tags = {"Matches"})),
            @RouterOperation(path = "/api/v1/matches/{id}/winner", method = RequestMethod.PATCH, beanClass = MatchHandler.class, beanMethod = "defineWinner", operation = @Operation(operationId = "defineWinner", summary = "Define match winner", tags = {"Matches"})),
            @RouterOperation(path = "/api/v1/matches/{id}/teams", method = RequestMethod.POST, beanClass = MatchHandler.class, beanMethod = "assignTeam", operation = @Operation(operationId = "assignTeam", summary = "Assign team to match", tags = {"Matches"})),
            @RouterOperation(path = "/api/v1/matches/{id}/teams/{teamId}", method = RequestMethod.DELETE, beanClass = MatchHandler.class, beanMethod = "removeTeam", operation = @Operation(operationId = "removeTeam", summary = "Remove team from match", tags = {"Matches"}))
    })
    public RouterFunction<ServerResponse> matchRouterFunction(MatchHandler matchHandler) {
        return RouterFunctions
                .route()
                .path("/api/v1/matches",
                        builder -> builder
                                .POST("", matchHandler::createMatch)
                                .GET("/{id}", matchHandler::getMatch)
                                .GET("/tournament/{tournamentId}", matchHandler::listMatchesByTournament)
                                .GET("/tournament/{tournamentId}/participant/{participantId}", matchHandler::listMatchesByParticipantAndTournament)
                                .GET("", matchHandler::listMatches)
                                .PUT("/{id}", matchHandler::updateMatch)
                                .PATCH("/{id}/status", matchHandler::updateMatchStatus)
                                .PATCH("/{id}/details", matchHandler::updateMatchDetails)
                                .PATCH("/{id}/cancel", matchHandler::cancelMatch)
                                .PATCH("/{id}/winner", matchHandler::defineWinner)
                                .POST("/{id}/teams", matchHandler::assignTeam)
                                .DELETE("/{id}/teams/{teamId}", matchHandler::removeTeam)
                )
                .build();
    }
}

package com.fc2o.api.router;

import com.fc2o.api.handler.TournamentTeamHandler;
import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * Router for tournament team endpoints.
 * Manages teams registered in specific tournaments.
 */
@Configuration
public class TournamentTeamRouter {

    @Bean
    @Order(1)
    @RouterOperations({
            @RouterOperation(path = "/api/v1/tournament-teams/tournament/{tournamentId}", method = RequestMethod.GET, beanClass = TournamentTeamHandler.class, beanMethod = "listTeamsInTournament", operation = @Operation(operationId = "listTeamsInTournament", summary = "List teams in tournament", tags = {"Tournament Teams"})),
            @RouterOperation(path = "/api/v1/tournament-teams/tournament/{tournamentId}/team/{teamId}", method = RequestMethod.GET, beanClass = TournamentTeamHandler.class, beanMethod = "getTeamInTournament", operation = @Operation(operationId = "getTeamInTournament", summary = "Get team in tournament", tags = {"Tournament Teams"})),
            @RouterOperation(path = "/api/v1/tournament-teams/tournament/{tournamentId}/team/{teamId}", method = RequestMethod.DELETE, beanClass = TournamentTeamHandler.class, beanMethod = "removeTeamFromTournament", operation = @Operation(operationId = "removeTeamFromTournament", summary = "Remove team from tournament", tags = {"Tournament Teams"})),
            @RouterOperation(path = "/api/v1/tournament-teams/tournament/{tournamentId}/team/{teamId}/exists", method = RequestMethod.GET, beanClass = TournamentTeamHandler.class, beanMethod = "checkTeamInTournament", operation = @Operation(operationId = "checkTeamInTournament", summary = "Check if team is in tournament", tags = {"Tournament Teams"}))
    })
    public RouterFunction<ServerResponse> tournamentTeamRouterFunction(TournamentTeamHandler handler) {
        return RouterFunctions.route()
                .path("/api/v1/tournament-teams", builder -> builder
                        // Public endpoints
                        .GET("/tournament/{tournamentId}", handler::listTeamsInTournament)
                        .GET("/tournament/{tournamentId}/team/{teamId}", handler::getTeamInTournament)
                        .GET("/tournament/{tournamentId}/team/{teamId}/exists", handler::checkTeamInTournament)
                        // Restricted endpoints - PROMOTER, MOD or ADMIN only
                        .DELETE("/tournament/{tournamentId}/team/{teamId}", handler::removeTeamFromTournament)
                )
                .build();
    }
}


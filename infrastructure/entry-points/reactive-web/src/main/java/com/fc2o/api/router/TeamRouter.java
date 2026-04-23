package com.fc2o.api.router;

import com.fc2o.api.handler.TeamHandler;
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
public class TeamRouter {

    @Bean
    @Order(1)
    @RouterOperations({
            @RouterOperation(path = "/api/v1/teams", method = RequestMethod.GET, beanClass = TeamHandler.class, beanMethod = "listTeams", operation = @Operation(operationId = "listTeams", summary = "List teams", tags = {"Teams"})),
            @RouterOperation(path = "/api/v1/teams/leader/{leaderUserId}", method = RequestMethod.GET, beanClass = TeamHandler.class, beanMethod = "listTeamsByLeader", operation = @Operation(operationId = "listTeamsByLeader", summary = "List teams by leader", tags = {"Teams"})),
            @RouterOperation(path = "/api/v1/teams/{id}", method = RequestMethod.GET, beanClass = TeamHandler.class, beanMethod = "getTeam", operation = @Operation(operationId = "getTeam", summary = "Get team by id", tags = {"Teams"})),
            @RouterOperation(path = "/api/v1/teams/{id}/members", method = RequestMethod.GET, beanClass = TeamHandler.class, beanMethod = "listTeamMembers", operation = @Operation(operationId = "listTeamMembers", summary = "List team members", tags = {"Teams"})),
            @RouterOperation(path = "/api/v1/teams", method = RequestMethod.POST, beanClass = TeamHandler.class, beanMethod = "createTeam", operation = @Operation(operationId = "createTeam", summary = "Create team", tags = {"Teams"})),
            @RouterOperation(path = "/api/v1/teams/{id}", method = RequestMethod.PUT, beanClass = TeamHandler.class, beanMethod = "updateTeam", operation = @Operation(operationId = "updateTeam", summary = "Update team", tags = {"Teams"})),
            @RouterOperation(path = "/api/v1/teams/{id}/members", method = RequestMethod.POST, beanClass = TeamHandler.class, beanMethod = "addTeamMember", operation = @Operation(operationId = "addTeamMember", summary = "Add team member", tags = {"Teams"})),
            @RouterOperation(path = "/api/v1/teams/{id}", method = RequestMethod.DELETE, beanClass = TeamHandler.class, beanMethod = "deleteTeam", operation = @Operation(operationId = "deleteTeam", summary = "Delete team", tags = {"Teams"})),
            @RouterOperation(path = "/api/v1/teams/{id}/members/{userId}", method = RequestMethod.DELETE, beanClass = TeamHandler.class, beanMethod = "removeTeamMember", operation = @Operation(operationId = "removeTeamMember", summary = "Remove team member", tags = {"Teams"}))
    })
    public RouterFunction<ServerResponse> teamRouterFunction(TeamHandler teamHandler) {
        return RouterFunctions.route()
                .path("/api/v1/teams", builder -> builder
                        .GET("", teamHandler::listTeams)
                        .GET("/leader/{leaderUserId}", teamHandler::listTeamsByLeader)
                        .GET("/{id}", teamHandler::getTeam)
                        .GET("/{id}/members", teamHandler::listTeamMembers)
                        .POST("", teamHandler::createTeam)
                        .PUT("/{id}", teamHandler::updateTeam)
                        .POST("/{id}/members", teamHandler::addTeamMember)
                        .DELETE("/{id}", teamHandler::deleteTeam)
                        .DELETE("/{id}/members/{userId}", teamHandler::removeTeamMember))
                .build();
    }
}


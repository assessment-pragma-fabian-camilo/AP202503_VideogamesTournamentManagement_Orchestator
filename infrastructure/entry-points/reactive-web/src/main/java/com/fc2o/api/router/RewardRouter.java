package com.fc2o.api.router;

import com.fc2o.api.handler.RewardHandler;
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
public class RewardRouter {

    @Bean
    @Order(1)
    @RouterOperations({
            @RouterOperation(path = "/api/v1/rewards/{id}", method = RequestMethod.GET, beanClass = RewardHandler.class, beanMethod = "getReward", operation = @Operation(operationId = "getReward", summary = "Get reward by id", tags = {"Rewards"})),
            @RouterOperation(path = "/api/v1/rewards/tournament/{tournamentId}", method = RequestMethod.GET, beanClass = RewardHandler.class, beanMethod = "listRewardsByTournament", operation = @Operation(operationId = "listRewardsByTournament", summary = "List rewards by tournament", tags = {"Rewards"})),
            @RouterOperation(path = "/api/v1/rewards", method = RequestMethod.POST, beanClass = RewardHandler.class, beanMethod = "createReward", operation = @Operation(operationId = "createReward", summary = "Create reward", tags = {"Rewards"})),
            @RouterOperation(path = "/api/v1/rewards/{id}", method = RequestMethod.PUT, beanClass = RewardHandler.class, beanMethod = "updateReward", operation = @Operation(operationId = "updateReward", summary = "Update reward", tags = {"Rewards"})),
            @RouterOperation(path = "/api/v1/rewards/{id}/team", method = RequestMethod.PATCH, beanClass = RewardHandler.class, beanMethod = "assignRewardToTeam", operation = @Operation(operationId = "assignRewardToTeam", summary = "Assign reward to team", tags = {"Rewards"}))
    })
    public RouterFunction<ServerResponse> rewardRouterFunction(RewardHandler rewardHandler) {
        return RouterFunctions.route()
                .path("/api/v1/rewards", builder -> builder
                        .GET("/{id}", rewardHandler::getReward)
                        .GET("/tournament/{tournamentId}", rewardHandler::listRewardsByTournament)
                        .POST("", rewardHandler::createReward)
                        .PUT("/{id}", rewardHandler::updateReward)
                        .PATCH("/{id}/team", rewardHandler::assignRewardToTeam))
                .build();
    }
}


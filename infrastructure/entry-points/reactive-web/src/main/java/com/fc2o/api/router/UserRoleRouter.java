package com.fc2o.api.router;

import com.fc2o.api.handler.UserRoleHandler;
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
public class UserRoleRouter {

    @Bean
    @Order(1)
    @RouterOperations({
            @RouterOperation(path = "/api/v1/user-roles/user/{userId}", method = RequestMethod.GET, beanClass = UserRoleHandler.class, beanMethod = "getUserRoles", operation = @Operation(operationId = "getUserRoles", summary = "Get user roles", tags = {"User Roles"})),
            @RouterOperation(path = "/api/v1/user-roles", method = RequestMethod.POST, beanClass = UserRoleHandler.class, beanMethod = "assignRole", operation = @Operation(operationId = "assignRole", summary = "Assign role", tags = {"User Roles"})),
            @RouterOperation(path = "/api/v1/user-roles/{id}", method = RequestMethod.DELETE, beanClass = UserRoleHandler.class, beanMethod = "removeRole", operation = @Operation(operationId = "removeRole", summary = "Remove role", tags = {"User Roles"}))
    })
    public RouterFunction<ServerResponse> userRoleRouterFunction(UserRoleHandler userRoleHandler) {
        return RouterFunctions.route()
                .path("/api/v1/user-roles", builder -> builder
                        .GET("/user/{userId}", userRoleHandler::getUserRoles)
                        .POST("", userRoleHandler::assignRole)
                        .DELETE("/{id}", userRoleHandler::removeRole))
                .build();
    }
}


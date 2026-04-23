package com.fc2o.api.router;

import com.fc2o.api.handler.CommissionHandler;
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
public class CommissionRouter {

    @Bean
    @Order(1)
    @RouterOperations({
            @RouterOperation(path = "/api/v1/commissions", method = RequestMethod.GET, beanClass = CommissionHandler.class, beanMethod = "listCommissions", operation = @Operation(operationId = "listCommissions", summary = "List commissions", tags = {"Commissions"})),
            @RouterOperation(path = "/api/v1/commissions/{commissionType}", method = RequestMethod.GET, beanClass = CommissionHandler.class, beanMethod = "getCommission", operation = @Operation(operationId = "getCommission", summary = "Get commission by type", tags = {"Commissions"})),
            @RouterOperation(path = "/api/v1/commissions", method = RequestMethod.PUT, beanClass = CommissionHandler.class, beanMethod = "updateCommission", operation = @Operation(operationId = "updateCommission", summary = "Update commission", tags = {"Commissions"}))
    })
    public RouterFunction<ServerResponse> commissionRouterFunction(CommissionHandler commissionHandler) {
        return RouterFunctions.route()
                .path("/api/v1/commissions", builder -> builder
                        .GET("/{commissionType}", commissionHandler::getCommission)
                        .GET("", commissionHandler::listCommissions)
                        .PUT("", commissionHandler::updateCommission))
                .build();
    }
}


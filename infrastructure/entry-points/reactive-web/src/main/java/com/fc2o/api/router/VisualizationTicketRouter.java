package com.fc2o.api.router;

import com.fc2o.api.handler.VisualizationTicketHandler;
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
public class VisualizationTicketRouter {

    @Bean
    @Order(1)
    @RouterOperations({
            @RouterOperation(path = "/api/v1/visualization-tickets", method = RequestMethod.POST, beanClass = VisualizationTicketHandler.class, beanMethod = "createTicket", operation = @Operation(operationId = "createVisualizationTicket", summary = "Create visualization ticket", tags = {"Visualization Tickets"})),
            @RouterOperation(path = "/api/v1/visualization-tickets/{id}", method = RequestMethod.GET, beanClass = VisualizationTicketHandler.class, beanMethod = "getTicket", operation = @Operation(operationId = "getVisualizationTicket", summary = "Get visualization ticket", tags = {"Visualization Tickets"})),
            @RouterOperation(path = "/api/v1/visualization-tickets/tournament/{tournamentId}", method = RequestMethod.GET, beanClass = VisualizationTicketHandler.class, beanMethod = "listTicketsByTournament", operation = @Operation(operationId = "listVisualizationTicketsByTournament", summary = "List visualization tickets by tournament", tags = {"Visualization Tickets"})),
            @RouterOperation(path = "/api/v1/visualization-tickets/{id}/status", method = RequestMethod.PATCH, beanClass = VisualizationTicketHandler.class, beanMethod = "updateTicketStatus", operation = @Operation(operationId = "updateVisualizationTicketStatus", summary = "Update visualization ticket status", tags = {"Visualization Tickets"})),
            @RouterOperation(path = "/api/v1/visualization-tickets/{id}/transaction-status", method = RequestMethod.PATCH, beanClass = VisualizationTicketHandler.class, beanMethod = "updateTicketTransactionStatus", operation = @Operation(operationId = "updateVisualizationTicketTransactionStatus", summary = "Update visualization ticket transaction status", tags = {"Visualization Tickets"})),
            @RouterOperation(path = "/api/v1/visualization-tickets/{id}/block", method = RequestMethod.PATCH, beanClass = VisualizationTicketHandler.class, beanMethod = "blockTicket", operation = @Operation(operationId = "blockVisualizationTicket", summary = "Block visualization ticket", tags = {"Visualization Tickets"})),
            @RouterOperation(path = "/api/v1/visualization-tickets/{id}/validate-qr", method = RequestMethod.PATCH, beanClass = VisualizationTicketHandler.class, beanMethod = "validateQr", operation = @Operation(operationId = "validateVisualizationTicketQr", summary = "Validate visualization ticket QR", tags = {"Visualization Tickets"}))
    })
    public RouterFunction<ServerResponse> visualizationTicketRouterFunction(VisualizationTicketHandler handler) {
        return RouterFunctions.route()
                .path("/api/v1/visualization-tickets", builder -> builder
                        .POST("", handler::createTicket)
                        .GET("/{id}", handler::getTicket)
                        .GET("/tournament/{tournamentId}", handler::listTicketsByTournament)
                        .PATCH("/{id}/status", handler::updateTicketStatus)
                        .PATCH("/{id}/transaction-status", handler::updateTicketTransactionStatus)
                        .PATCH("/{id}/block", handler::blockTicket)
                        .PATCH("/{id}/validate-qr", handler::validateQr))
                .build();
    }
}


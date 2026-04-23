package com.fc2o.api.router;

import com.fc2o.api.handler.ParticipationTicketHandler;
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
public class ParticipationTicketRouter {

    @Bean
    @Order(1)
    @RouterOperations({
            @RouterOperation(path = "/api/v1/participation-tickets", method = RequestMethod.POST, beanClass = ParticipationTicketHandler.class, beanMethod = "createTicket", operation = @Operation(operationId = "createParticipationTicket", summary = "Create participation ticket", tags = {"Participation Tickets"})),
            @RouterOperation(path = "/api/v1/participation-tickets/{id}", method = RequestMethod.GET, beanClass = ParticipationTicketHandler.class, beanMethod = "getTicket", operation = @Operation(operationId = "getParticipationTicket", summary = "Get participation ticket", tags = {"Participation Tickets"})),
            @RouterOperation(path = "/api/v1/participation-tickets/tournament/{tournamentId}", method = RequestMethod.GET, beanClass = ParticipationTicketHandler.class, beanMethod = "listTicketsByTournament", operation = @Operation(operationId = "listParticipationTicketsByTournament", summary = "List participation tickets by tournament", tags = {"Participation Tickets"})),
            @RouterOperation(path = "/api/v1/participation-tickets/{id}/status", method = RequestMethod.PATCH, beanClass = ParticipationTicketHandler.class, beanMethod = "updateTicketStatus", operation = @Operation(operationId = "updateParticipationTicketStatus", summary = "Update participation ticket status", tags = {"Participation Tickets"})),
            @RouterOperation(path = "/api/v1/participation-tickets/{id}/transaction-status", method = RequestMethod.PATCH, beanClass = ParticipationTicketHandler.class, beanMethod = "updateTicketTransactionStatus", operation = @Operation(operationId = "updateParticipationTicketTransactionStatus", summary = "Update participation ticket transaction status", tags = {"Participation Tickets"})),
            @RouterOperation(path = "/api/v1/participation-tickets/{id}/block", method = RequestMethod.PATCH, beanClass = ParticipationTicketHandler.class, beanMethod = "blockTicket", operation = @Operation(operationId = "blockParticipationTicket", summary = "Block participation ticket", tags = {"Participation Tickets"})),
            @RouterOperation(path = "/api/v1/participation-tickets/{id}/validate-qr", method = RequestMethod.PATCH, beanClass = ParticipationTicketHandler.class, beanMethod = "validateQr", operation = @Operation(operationId = "validateParticipationTicketQr", summary = "Validate participation ticket QR", tags = {"Participation Tickets"}))
    })
    public RouterFunction<ServerResponse> participationTicketRouterFunction(ParticipationTicketHandler handler) {
        return RouterFunctions.route()
                .path("/api/v1/participation-tickets", builder -> builder
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


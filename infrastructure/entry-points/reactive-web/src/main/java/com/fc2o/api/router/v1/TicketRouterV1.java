package com.fc2o.api.router.v1;

import com.fc2o.api.handler.v1.TicketHandlerV1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class TicketRouterV1 {
  @Bean
  @Order(2)
  public RouterFunction<ServerResponse> ticketRouterFunction(TicketHandlerV1 handlerV1) {
    return RouterFunctions
      .route()
      .path("/api/v1/orchestrator",
        builder -> builder
          .POST("/tournaments/{tournamentId}/users/{userId}/tickets", handlerV1::register)
          .PATCH("/users/{userId}/tickets/{ticketId}/block", handlerV1::block)
      )
      .build();
  }
}

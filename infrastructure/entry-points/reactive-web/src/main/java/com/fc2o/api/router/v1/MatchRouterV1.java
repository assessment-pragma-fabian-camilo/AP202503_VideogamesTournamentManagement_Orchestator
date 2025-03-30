package com.fc2o.api.router.v1;

import com.fc2o.api.handler.v1.MatchHandlerV1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class MatchRouterV1 {
  @Bean
  @Order(1)
  public RouterFunction<ServerResponse> matchRouterFunction(MatchHandlerV1 handlerV1) {
    return RouterFunctions
      .route()
      .path("/api/v1",
        builder -> builder
          .POST("/tournaments/{tournamentId}/matches", handlerV1::register)
          .PATCH("/tournaments/{tournamentId}/matches/{matchId}/cancel", handlerV1::cancel)
          .PATCH("/tournaments/{tournamentId}/matches/{matchId}/finalize", handlerV1::finalize)
          .PATCH("/tournaments/{tournamentId}/matches/{matchId}/start", handlerV1::start)
      )
      .build();
  }
}

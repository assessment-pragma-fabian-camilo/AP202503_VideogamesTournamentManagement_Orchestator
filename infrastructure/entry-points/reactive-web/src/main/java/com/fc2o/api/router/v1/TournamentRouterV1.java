package com.fc2o.api.router.v1;

import com.fc2o.api.handler.v1.TournamentHandlerV1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class TournamentRouterV1 {
  @Bean
  @Order(1)
  public RouterFunction<ServerResponse> tournamentRouterFunction(TournamentHandlerV1 handlerV1) {
    return RouterFunctions
      .route()
      .path("/api/v1/tournaments",
        builder -> builder
          .POST("", handlerV1::register)
          .POST("/{tournamentId}/moderators", handlerV1::addMod)
          .PATCH("/{tournamentId}/cancel", handlerV1::cancel)
          .DELETE("/{tournamentId}/users/{userId}", handlerV1::disqualifyParticipant)
          .PATCH("/{tournamentId}/finalize", handlerV1::finalize)
          .POST("/{tournamentId}/users/{userId}/pre-register", handlerV1::preRegisterParticipant)
          .POST("/{tournamentId}/users/{userId}/tickets/{ticketId}/register", handlerV1::registerParticipant)
          .PATCH("/{tournamentId}/users/reschedule", handlerV1::reschedule)
          .PATCH("/{tournamentId}/users/start", handlerV1::start)
      )
      .build();
  }
}

package com.fc2o.api.router;

import com.fc2o.api.handler.StreamHandler;
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
public class StreamRouter {

    @Bean
    @Order(1)
    @RouterOperations({
            @RouterOperation(path = "/api/v1/streams/{id}", method = RequestMethod.GET, beanClass = StreamHandler.class, beanMethod = "getStream", operation = @Operation(operationId = "getStream", summary = "Get stream by id", tags = {"Streams"})),
            @RouterOperation(path = "/api/v1/streams/tournament/{tournamentId}", method = RequestMethod.GET, beanClass = StreamHandler.class, beanMethod = "listStreamsByTournament", operation = @Operation(operationId = "listStreamsByTournament", summary = "List streams by tournament", tags = {"Streams"})),
            @RouterOperation(path = "/api/v1/streams", method = RequestMethod.POST, beanClass = StreamHandler.class, beanMethod = "createStream", operation = @Operation(operationId = "createStream", summary = "Create stream", tags = {"Streams"})),
            @RouterOperation(path = "/api/v1/streams/{id}", method = RequestMethod.PUT, beanClass = StreamHandler.class, beanMethod = "updateStream", operation = @Operation(operationId = "updateStream", summary = "Update stream", tags = {"Streams"})),
            @RouterOperation(path = "/api/v1/streams/{id}", method = RequestMethod.DELETE, beanClass = StreamHandler.class, beanMethod = "deleteStream", operation = @Operation(operationId = "deleteStream", summary = "Delete stream", tags = {"Streams"}))
    })
    public RouterFunction<ServerResponse> streamRouterFunction(StreamHandler streamHandler) {
        return RouterFunctions.route()
                .path("/api/v1/streams", builder -> builder
                        .GET("/{id}", streamHandler::getStream)
                        .GET("/tournament/{tournamentId}", streamHandler::listStreamsByTournament)
                        .POST("", streamHandler::createStream)
                        .PUT("/{id}", streamHandler::updateStream)
                        .DELETE("/{id}", streamHandler::deleteStream))
                .build();
    }
}


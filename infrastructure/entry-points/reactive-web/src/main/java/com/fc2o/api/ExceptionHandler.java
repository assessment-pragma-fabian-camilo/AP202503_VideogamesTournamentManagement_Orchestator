package com.fc2o.api;

import com.fc2o.api.dto.response.generic.ErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.webflux.autoconfigure.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.webflux.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;

@Log4j2
@Order(-2)
@Component
public class ExceptionHandler extends AbstractErrorWebExceptionHandler {
    public ExceptionHandler(
            ErrorAttributes errorAttributes,
            WebProperties resources,
            ApplicationContext applicationContext,
            ServerCodecConfigurer serverCodecConfigurer
    ) {
        super(errorAttributes, resources.getResources(), applicationContext);
        this.setMessageWriters(serverCodecConfigurer.getWriters());
    }

    @Override
    protected @NonNull RouterFunction<ServerResponse> getRoutingFunction(@NonNull ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest serverRequest) {
        var error = getError(serverRequest);
        assert error != null;
        return Mono.error(error)
                .onErrorResume(Exception.class, this::buildResponse)
                .cast(ServerResponse.class);
    }

    private Mono<ServerResponse> buildResponse(Throwable e) {
        return ServerResponse
                .status(500)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(ErrorResponse.builder().code(500).message(e.getMessage()));
    }
}
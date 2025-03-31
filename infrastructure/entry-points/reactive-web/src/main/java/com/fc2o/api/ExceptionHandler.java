package com.fc2o.api;

import com.fc2o.api.dto.ErrorResponse;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

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
  protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
    return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
  }

  private Mono<ServerResponse> renderErrorResponse(ServerRequest serverRequest) {
    return Mono.error(getError(serverRequest))
      .onErrorResume(Exception.class, e ->
        ServerResponse
          .badRequest()
          .bodyValue(
            ErrorResponse.builder()
              .dateTime(LocalDateTime.now().toString())
              .errorMessage(e.getMessage())
              .build()
          )
      )
      .cast(ServerResponse.class);
  }
}

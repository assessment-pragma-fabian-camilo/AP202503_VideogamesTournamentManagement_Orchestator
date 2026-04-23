package com.fc2o.api.handler.v1;

import com.fc2o.api.dto.match.req.finalize.FinalizeMatchRequestDto;
import com.fc2o.api.dto.match.req.register.RegisterMatchRequestDto;
import com.fc2o.api.mapper.match.CancelMatchMapper;
import com.fc2o.api.mapper.match.FinalizeMatchMapper;
import com.fc2o.api.mapper.match.RegisterMatchMapper;
import com.fc2o.api.mapper.match.StartMatchMapper;
import com.fc2o.usecase.match.business.CancelMatchUseCase;
import com.fc2o.usecase.match.business.FinalizeMatchUseCase;
import com.fc2o.usecase.match.business.RegisterMatchUseCase;
import com.fc2o.usecase.match.business.StartMatchUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

@Log4j2
@Component
@RequiredArgsConstructor
public class MatchHandlerV1 {

  private final CancelMatchUseCase cancelMatchUseCase;
  private final FinalizeMatchUseCase finalizeMatchUseCase;
  private final RegisterMatchUseCase registerMatchUseCase;
  private final StartMatchUseCase startMatchUseCase;
  private final RegisterMatchMapper registerMatchMapper;
  private final CancelMatchMapper cancelMatchMapper;
  private final FinalizeMatchMapper finalizeMatchMapper;
  private final StartMatchMapper startMatchMapper;

  private final String USER_ID = UUID.randomUUID().toString();

  //  @PreAuthorize("hasRole('permissionGETOther')")
  public Mono<ServerResponse> register(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(RegisterMatchRequestDto.class)
      .doOnNext(log::info)
      .map(registerMatchMapper::toMatch)
      .flatMap(match ->
        registerMatchUseCase
          .registerMatch(serverRequest.pathVariables().get("tournamentId"), match, USER_ID)
      )
      .map(registerMatchMapper::toRegisterMatchResponseDto)
      .doOnNext(log::info)
      .flatMap(response ->
        ServerResponse
          .created(URI.create(String.format("%s/%s", serverRequest.path(), response.match().id())))
          .bodyValue(response)
      );
  }

  public Mono<ServerResponse> cancel(ServerRequest serverRequest) {
    log.info(serverRequest);
    return cancelMatchUseCase
      .cancelMatch(
        serverRequest.pathVariables().get("matchId"),
        serverRequest.pathVariables().get("tournamentId"),
        USER_ID
      )
      .map(cancelMatchMapper::toCancelMatchResponseDto)
      .doOnNext(log::info)
      .flatMap(response -> ServerResponse.ok().bodyValue(response));
  }

  public Mono<ServerResponse> finalize(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(FinalizeMatchRequestDto.class)
      .doOnNext(log::info)
      .flatMap(dto ->
        finalizeMatchUseCase.finalizeMatch(
          serverRequest.pathVariables().get("tournamentId"),
          serverRequest.pathVariables().get("matchId"),
          dto.match().winnerId(),
          USER_ID
        )
      )
      .map(finalizeMatchMapper::toFinalizeMatchResponseDto)
      .doOnNext(log::info)
      .flatMap(response -> ServerResponse.ok().bodyValue(response));
  }

  public Mono<ServerResponse> start(ServerRequest serverRequest) {
    log.info(serverRequest);
    return startMatchUseCase.startMatch(
        serverRequest.pathVariables().get("tournamentId"),
        serverRequest.pathVariables().get("matchId"),
        USER_ID
      )
      .map(startMatchMapper::toStartMatchResponseDto)
      .doOnNext(log::info)
      .flatMap(response -> ServerResponse.ok().bodyValue(response));
  }
}

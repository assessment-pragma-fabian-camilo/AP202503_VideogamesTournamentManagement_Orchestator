package com.fc2o.api.handler.v1;

import com.fc2o.api.mapper.ticket.BlockTicketMapper;
import com.fc2o.api.mapper.ticket.RegisterTicketMapper;
import com.fc2o.usecase.ticket.business.BlockTicketUseCase;
import com.fc2o.usecase.ticket.business.RegisterTicketUseCase;
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
public class TicketHandlerV1 {

  private final RegisterTicketUseCase registerTicketUseCase;
  private final BlockTicketUseCase blockTicketUseCase;
  private final RegisterTicketMapper registerTicketMapper;
  private final BlockTicketMapper blockTicketMapper;

  //  @PreAuthorize("hasRole('permissionGETOther')")
  public Mono<ServerResponse> register(ServerRequest serverRequest) {
    log.info(serverRequest);
    return Mono.just(serverRequest.queryParams())
      .filter(map -> map.get("type").contains("PARTICIPATION"))
      .flatMap(map ->
        registerTicketUseCase.registerParticipationTicket(
          UUID.fromString(serverRequest.pathVariables().get("tournamentId")),
          UUID.fromString(serverRequest.pathVariables().get("userId"))
        )
      )
      .map(registerTicketMapper::toRegisterTicketResponseDto)
      .doOnNext(log::info)
      .flatMap(response ->
        ServerResponse
          .created(URI.create(String.format("%s/%s", serverRequest.path(), response.ticket().id())))
          .bodyValue(response)
      );
  }

  public Mono<ServerResponse> block(ServerRequest serverRequest) {
    log.info(serverRequest);
    return blockTicketUseCase
      .blockTicket(
        UUID.fromString(serverRequest.pathVariables().get("ticketId")),
        UUID.fromString(serverRequest.pathVariables().get("userId"))
      )
      .map(blockTicketMapper::toBlockTicketResponseDto)
      .doOnNext(log::info)
      .flatMap(response -> ServerResponse.ok().bodyValue(response));
  }
}

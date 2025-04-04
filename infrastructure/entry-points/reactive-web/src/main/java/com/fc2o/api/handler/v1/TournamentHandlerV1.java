package com.fc2o.api.handler.v1;

import com.fc2o.api.dto.tournament.req.add_mod.AddModTournamentRequestDto;
import com.fc2o.api.dto.tournament.req.cancel.CancelTournamentRequestDto;
import com.fc2o.api.dto.tournament.req.finalize.FinalizeTournamentRequestDto;
import com.fc2o.api.dto.tournament.req.register.RegisterTournamentRequestDto;
import com.fc2o.api.mapper.tournament.*;
import com.fc2o.model.tournament.Tournament;
import com.fc2o.usecase.tournament.business.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Log4j2
@Component
@RequiredArgsConstructor
public class TournamentHandlerV1 {

  private final RegisterTournamentUseCase registerTournamentUseCase;
  private final AddModTournamentUseCase addModTournamentUseCase;
  private final CancelTournamentUseCase cancelTournamentUseCase;
  private final DisqualifyParticipantUseCase disqualifyParticipantUseCase;
  private final FinalizeTournamentUseCase finalizeTournamentUseCase;
  private final PreRegisterParticipantUseCase preRegisterParticipantUseCase;
  private final RegisterParticipantUseCase registerParticipantUseCase;
  private final RescheduleTournamentUseCase rescheduleTournamentUseCase;
  private final StartTournamentUseCase startTournamentUseCase;
  private final RegisterTournamentMapper registerTournamentMapper;
  private final AddModTournamentMapper addModTournamentMapper;
  private final CancelTournamentMapper cancelTournamentMapper;
  private final FinalizeTournamentMapper finalizeTournamentMapper;
  private final RescheduleTournamentMapper rescheduleTournamentMapper;
  private final StartTournamentMapper startTournamentMapper;
  private final PreRegisterParticipantMapper preRegisterParticipantMapper;
  private final String USER_ID = "recoyubCuSes3tgai";

  //  @PreAuthorize("hasRole('permissionGETOther')")
  public Mono<ServerResponse> register(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(RegisterTournamentRequestDto.class)
      .doOnNext(log::info)
      .map(registerTournamentMapper::toTournament)
      .map(tournament -> tournament.toBuilder().promoterId(USER_ID).build())
      .flatMap(registerTournamentUseCase::registerTournament)
      .map(registerTournamentMapper::toTournamentResponseDto)
      .doOnNext(log::info)
      .flatMap(response ->
        ServerResponse
          .created(URI.create(String.format("%s/%s", serverRequest.path(), response.tournament().id())))
          .bodyValue(response)
      );
  }

  public Mono<ServerResponse> addMod(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(AddModTournamentRequestDto.class)
      .doOnNext(log::info)
      .map(addModTournamentMapper::toTournament)
      .map(tournament -> tournament.toBuilder().id(serverRequest.pathVariables().get("tournamentId")).build())
      .flatMap(tournament -> addModTournamentUseCase.addMod(tournament, USER_ID))
      .map(addModTournamentMapper::toAddModTournamentResponseDto)
      .doOnNext(log::info)
      .flatMap(response -> ServerResponse.ok().bodyValue(response));
  }

  public Mono<ServerResponse> cancel(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(CancelTournamentRequestDto.class)
      .doOnNext(log::info)
      .map(cancelTournamentMapper::toTournament)
      .flatMap(tournament -> cancelTournamentUseCase.cancel(tournament, USER_ID))
      .map(cancelTournamentMapper::toCancelTournamentResponseDto)
      .doOnNext(log::info)
      .flatMap(response -> ServerResponse.ok().bodyValue(response));
  }

  public Mono<ServerResponse> disqualifyParticipant(ServerRequest serverRequest) {
    log.info(serverRequest);
    return disqualifyParticipantUseCase
      .disqualifyParticipant(
        serverRequest.pathVariables().get("tournamentId"),
        serverRequest.pathVariables().get("userId"),
        USER_ID
      )
      .flatMap(response -> ServerResponse.ok().bodyValue(""));
  }

  public Mono<ServerResponse> finalize(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(FinalizeTournamentRequestDto.class)
      .doOnNext(log::info)
      .map(finalizeTournamentMapper::toReward)
      .flatMap(reward ->
        finalizeTournamentUseCase
          .finalize(serverRequest.pathVariables().get("tournamentId"), reward, USER_ID)
      )
      .map(objects -> finalizeTournamentMapper.toFinalizeTournamentResponseDto(objects.getT1(), objects.getT2()))
      .doOnNext(log::info)
      .flatMap(response -> ServerResponse.ok().bodyValue(response));
  }

  public Mono<ServerResponse> preRegisterParticipant(ServerRequest serverRequest) {
    log.info(serverRequest);
    return preRegisterParticipantUseCase
      .preRegisterParticipant(
        serverRequest.pathVariables().get("tournamentId"),
        serverRequest.pathVariables().get("userId")
      )
      .map(preRegisterParticipantMapper::toPreRegisterParticipantResponseDto)
      .flatMap(response -> ServerResponse.ok().bodyValue(response));
  }

  public Mono<ServerResponse> registerParticipant(ServerRequest serverRequest) {
    log.info(serverRequest);
    return registerParticipantUseCase
      .registerParticipant(
        serverRequest.pathVariables().get("ticketId"),
        serverRequest.pathVariables().get("userId"),
        serverRequest.pathVariables().get("tournamentId")
      )
      .flatMap(response -> ServerResponse.ok().bodyValue(""));
  }

  public Mono<ServerResponse> reschedule(ServerRequest serverRequest) {
    log.info(serverRequest);
    Tournament tournament = Tournament.builder()
      .dateStart(serverRequest.queryParams().get("dateStart").getFirst())
      .dateEnd(serverRequest.queryParams().get("dateEnd").getFirst())
      .id(serverRequest.pathVariables().get("tournamentId"))
      .build();
    serverRequest.queryParams();
    return rescheduleTournamentUseCase
      .reschedule(tournament, USER_ID)
      .map(rescheduleTournamentMapper::toRescheduleTournamentResponseDto)
      .doOnNext(log::info)
      .flatMap(response -> ServerResponse.ok().bodyValue(response));
  }

  public Mono<ServerResponse> start(ServerRequest serverRequest) {
    log.info(serverRequest);
    return startTournamentUseCase.start(serverRequest.pathVariables().get("tournamentId"), USER_ID)
      .map(startTournamentMapper::toStartTournamentResponseDto)
      .doOnNext(log::info)
      .flatMap(response -> ServerResponse.ok().bodyValue(response));
  }
}

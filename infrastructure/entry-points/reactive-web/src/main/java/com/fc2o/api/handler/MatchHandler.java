package com.fc2o.api.handler;

import com.fc2o.api.dto.request.match.AssignTeamToMatchRequest;
import com.fc2o.api.dto.request.match.CreateMatchRequest;
import com.fc2o.api.dto.request.match.DefineMatchWinnerRequest;
import com.fc2o.api.dto.request.match.UpdateMatchDetailsRequest;
import com.fc2o.api.dto.request.match.UpdateMatchRequest;
import com.fc2o.api.dto.request.match.UpdateMatchStatusRequest;
import com.fc2o.api.dto.response.generic.ApiResponse;
import com.fc2o.api.mapper.match.MatchMapper;
import com.fc2o.api.security.DomainAuthorizationService;
import com.fc2o.api.validator.ValidatorHandler;
import com.fc2o.usecase.match.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class MatchHandler {
    private final CreateMatchUseCase createMatchUseCase;
    private final GetMatchUseCase getMatchUseCase;
    private final ListMatchesUseCase listMatchesUseCase;
    private final UpdateMatchUseCase updateMatchUseCase;
    private final UpdateMatchStatusUseCase updateMatchStatusUseCase;
    private final UpdateMatchDetailsUseCase updateMatchDetailsUseCase;
    private final CancelMatchUseCase cancelMatchUseCase;
    private final AssignTeamsToMatchUseCase assignTeamsToMatchUseCase;
    private final RemoveTeamFromMatchUseCase removeTeamFromMatchUseCase;
    private final DefineMatchWinnerUseCase defineMatchWinnerUseCase;
    private final MatchMapper matchMapper;
    private final ValidatorHandler validatorHandler;
    private final DomainAuthorizationService authorizationService;

    @PreAuthorize("hasAnyRole('PROMOTER','MOD','MODERATOR','ADMIN')")
    public Mono<ServerResponse> createMatch(ServerRequest request) {
        return request.bodyToMono(CreateMatchRequest.class)
                .doOnNext(validatorHandler::validate)
                .flatMap(dto -> authorizationService.authorizeTournamentManagement(dto.tournamentId(), true)
                        .then(createMatchUseCase.execute(dto.tournamentId(), dto.dateStart(), dto.timeStart())))
                .flatMap(match -> ServerResponse
                    .created(URI.create("/api/v1/matches/" + match.id()))
                    .bodyValue(ApiResponse.created(matchMapper.toResponse(match))));
    }

    @PreAuthorize("isAuthenticated()")
    public Mono<ServerResponse> getMatch(ServerRequest request) {
        String matchId = request.pathVariable("id");
        return getMatchUseCase.execute(matchId)
                .flatMap(match -> ServerResponse
                    .ok()
                    .bodyValue(ApiResponse.success(matchMapper.toResponse(match))));
    }

    @PreAuthorize("isAuthenticated()")
    public Mono<ServerResponse> listMatches(ServerRequest request) {
        return listMatchesUseCase.executeAll()
                .map(matchMapper::toResponse)
                .collectList()
                .flatMap(matches -> ServerResponse
                    .ok()
                    .bodyValue(ApiResponse.success(matches)));
    }

    @PreAuthorize("isAuthenticated()")
    public Mono<ServerResponse> listMatchesByTournament(ServerRequest request) {
        String tournamentId = request.pathVariable("tournamentId");
        return listMatchesUseCase.executeByTournament(tournamentId)
                .map(matchMapper::toResponse)
                .collectList()
                .flatMap(matches -> ServerResponse.ok().bodyValue(ApiResponse.success(matches)));
    }

    @PreAuthorize("isAuthenticated()")
    public Mono<ServerResponse> listMatchesByParticipantAndTournament(ServerRequest request) {
        String tournamentId = request.pathVariable("tournamentId");
        String participantId = request.pathVariable("participantId");
        return listMatchesUseCase.executeByParticipantAndTournament(participantId, tournamentId)
                .map(matchMapper::toResponse)
                .collectList()
                .flatMap(matches -> ServerResponse.ok().bodyValue(ApiResponse.success(matches)));
    }

    @PreAuthorize("hasAnyRole('PROMOTER','MOD','MODERATOR','ADMIN')")
    public Mono<ServerResponse> updateMatch(ServerRequest request) {
        String matchId = request.pathVariable("id");
        return authorizationService.authorizeMatchManagement(matchId, true)
                .then(request.bodyToMono(UpdateMatchRequest.class).doOnNext(validatorHandler::validate))
                .flatMap(body -> updateMatchUseCase.execute(matchId, body.dateTimeStart()))
                .map(matchMapper::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }

    @PreAuthorize("hasAnyRole('PROMOTER','MOD','MODERATOR','ADMIN')")
    public Mono<ServerResponse> updateMatchStatus(ServerRequest request) {
        String matchId = request.pathVariable("id");
        return authorizationService.authorizeMatchManagement(matchId, true)
                .then(request.bodyToMono(UpdateMatchStatusRequest.class).doOnNext(validatorHandler::validate))
                .flatMap(body -> updateMatchStatusUseCase.execute(matchId, body.status()))
                .map(matchMapper::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }

    @PreAuthorize("hasAnyRole('PROMOTER','MOD','MODERATOR','ADMIN')")
    public Mono<ServerResponse> updateMatchDetails(ServerRequest request) {
        String matchId = request.pathVariable("id");
        return authorizationService.authorizeMatchManagement(matchId, true)
                .then(request.bodyToMono(UpdateMatchDetailsRequest.class).doOnNext(validatorHandler::validate))
                .flatMap(body -> updateMatchDetailsUseCase.execute(matchId, body.matchDetails()))
                .map(matchMapper::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }

    @PreAuthorize("hasAnyRole('PROMOTER','MOD','MODERATOR','ADMIN')")
    public Mono<ServerResponse> cancelMatch(ServerRequest request) {
        String matchId = request.pathVariable("id");
        return authorizationService.authorizeMatchManagement(matchId, true)
                .then(cancelMatchUseCase.execute(matchId))
                .map(matchMapper::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }

    @PreAuthorize("hasAnyRole('PROMOTER','MOD','MODERATOR','ADMIN')")
    public Mono<ServerResponse> assignTeam(ServerRequest request) {
        String matchId = request.pathVariable("id");
        return authorizationService.authorizeMatchManagement(matchId, true)
                .then(request.bodyToMono(AssignTeamToMatchRequest.class).doOnNext(validatorHandler::validate))
                .flatMap(body -> assignTeamsToMatchUseCase.execute(matchId, body.teamId()))
                .map(matchMapper::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }

    @PreAuthorize("hasAnyRole('PROMOTER','MOD','MODERATOR','ADMIN')")
    public Mono<ServerResponse> removeTeam(ServerRequest request) {
        String matchId = request.pathVariable("id");
        String teamId = request.pathVariable("teamId");
        return authorizationService.authorizeMatchManagement(matchId, true)
                .then(removeTeamFromMatchUseCase.execute(matchId, teamId))
                .map(matchMapper::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }

    @PreAuthorize("hasAnyRole('PROMOTER','MOD','MODERATOR','ADMIN')")
    public Mono<ServerResponse> defineWinner(ServerRequest request) {
        String matchId = request.pathVariable("id");
        return authorizationService.authorizeMatchManagement(matchId, true)
                .then(request.bodyToMono(DefineMatchWinnerRequest.class).doOnNext(validatorHandler::validate))
                .flatMap(body -> defineMatchWinnerUseCase.execute(matchId, body.winnerTeamId()))
                .map(matchMapper::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }
}


package com.fc2o.api.handler;

import com.fc2o.api.config.JwtUserContextExtractor;
import com.fc2o.api.dto.request.tournament.CreateTournamentRequest;
import com.fc2o.api.dto.request.tournament.UpdatePlaceLimitsRequest;
import com.fc2o.api.dto.request.tournament.UpdateTournamentRequest;
import com.fc2o.api.dto.request.tournament.UpdateTournamentRulesRequest;
import com.fc2o.api.dto.request.tournament.UpdateTournamentStatusRequest;
import com.fc2o.api.dto.request.tournament.*;
import com.fc2o.api.dto.response.generic.ApiResponse;
import com.fc2o.api.mapper.tournament.TournamentMapper;
import com.fc2o.api.security.DomainAuthorizationService;
import com.fc2o.api.validator.ValidatorHandler;
import com.fc2o.usecase.tournament.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class TournamentHandler {

    private final CreateTournamentUseCase createTournamentUseCase;
    private final GetTournamentUseCase getTournamentUseCase;
    private final ListTournamentsUseCase listTournamentsUseCase;
    private final CancelTournamentUseCase cancelTournamentUseCase;
    private final CompleteTournamentUseCase completeTournamentUseCase;
    private final UpdatePlaceLimitsUseCase updatePlaceLimitsUseCase;
    private final UpdateTournamentRulesUseCase updateTournamentRulesUseCase;
    private final UpdateTournamentStatusUseCase updateTournamentStatusUseCase;
    private final UpdateTournamentUseCase updateTournamentUseCase;
    private final TournamentMapper tournamentMapper;
    private final ValidatorHandler validatorHandler;
    private final JwtUserContextExtractor jwtUserContextExtractor;
    private final DomainAuthorizationService authorizationService;

    @PreAuthorize("isAuthenticated()")
    public Mono<ServerResponse> getTournament(ServerRequest request) {
        String tournamentId = request.pathVariable("id");
        return getTournamentUseCase.execute(tournamentId)
                .map(tournamentMapper::toTournamentResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }

    @PreAuthorize("isAuthenticated()")
    public Mono<ServerResponse> listTournaments(ServerRequest request) {
        return listTournamentsUseCase.executeAll()
                .map(tournamentMapper::toTournamentResponse)
                .collectList()
                .flatMap(tournaments -> ServerResponse.ok().bodyValue(ApiResponse.success(tournaments)));
    }

    @PreAuthorize("isAuthenticated()")
    public Mono<ServerResponse> listTournamentsByPromoter(ServerRequest request) {
        String promoterId = request.pathVariable("promoterId");
        return listTournamentsUseCase.executeByPromoter(promoterId)
                .map(tournamentMapper::toTournamentResponse)
                .collectList()
                .flatMap(tournaments -> ServerResponse.ok().bodyValue(ApiResponse.success(tournaments)));
    }

    @PreAuthorize("hasAnyRole('PROMOTER','ADMIN')")
    public Mono<ServerResponse> createTournament(ServerRequest request) {
        return jwtUserContextExtractor.extractUserId()
                .zipWith(request.bodyToMono(CreateTournamentRequest.class)
                        .doOnNext(validatorHandler::validate))
                .map(tuple -> tournamentMapper.toTournamentFromCreateRequest(tuple.getT2())
                        .toBuilder()
                        .promoterUserId(tuple.getT1())
                        .build())
                .flatMap(createTournamentUseCase::execute)
                .map(tournamentMapper::toTournamentResponse)
                .flatMap(response -> ServerResponse
                        .created(URI.create("/api/v1/tournaments/" + response.id()))
                        .bodyValue(ApiResponse.created(response)));
    }

    @PreAuthorize("hasAnyRole('PROMOTER','MOD','MODERATOR','ADMIN')")
    public Mono<ServerResponse> updateTournament(ServerRequest request) {
        String tournamentId = request.pathVariable("id");
        return authorizationService.authorizeTournamentManagement(tournamentId, true)
                .then(request.bodyToMono(UpdateTournamentRequest.class)
                        .doOnNext(validatorHandler::validate))
                .flatMap(req -> updateTournamentUseCase.execute(tournamentId, req.name(), req.dateStart(), req.dateEnd()))
                .map(tournamentMapper::toTournamentResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }

    @PreAuthorize("hasAnyRole('PROMOTER','ADMIN')")
    public Mono<ServerResponse> cancelTournament(ServerRequest request) {
        String tournamentId = request.pathVariable("id");
        return authorizationService.authorizeTournamentManagement(tournamentId, false)
                .then(cancelTournamentUseCase.execute(tournamentId))
                .map(tournamentMapper::toTournamentResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }

    @PreAuthorize("hasAnyRole('PROMOTER','MOD','MODERATOR','ADMIN')")
    public Mono<ServerResponse> completeTournament(ServerRequest request) {
        String tournamentId = request.pathVariable("id");
        return authorizationService.authorizeTournamentManagement(tournamentId, true)
                .then(completeTournamentUseCase.execute(tournamentId))
                .map(tournamentMapper::toTournamentResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }

    @PreAuthorize("hasAnyRole('PROMOTER','ADMIN')")
    public Mono<ServerResponse> updatePlaceLimits(ServerRequest request) {
        String tournamentId = request.pathVariable("id");
        return authorizationService.authorizeTournamentManagement(tournamentId, false)
                .then(request.bodyToMono(UpdatePlaceLimitsRequest.class)
                        .doOnNext(validatorHandler::validate))
                .flatMap(req -> updatePlaceLimitsUseCase.execute(
                        tournamentId,
                        req.placeLimit(),
                        req.placeMinimum()))
                .map(tournamentMapper::toTournamentResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }

    @PreAuthorize("hasAnyRole('PROMOTER','MOD','MODERATOR','ADMIN')")
    public Mono<ServerResponse> updateTournamentRules(ServerRequest request) {
        String tournamentId = request.pathVariable("id");
        return authorizationService.authorizeTournamentManagement(tournamentId, true)
                .then(request.bodyToMono(UpdateTournamentRulesRequest.class)
                        .doOnNext(validatorHandler::validate))
                .flatMap(req -> updateTournamentRulesUseCase.execute(
                        tournamentId,
                        req.rules(),
                        req.description()))
                .map(tournamentMapper::toTournamentResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }

    @PreAuthorize("hasAnyRole('PROMOTER','ADMIN')")
    public Mono<ServerResponse> updateTournamentStatus(ServerRequest request) {
        String tournamentId = request.pathVariable("id");
        return authorizationService.authorizeTournamentManagement(tournamentId, false)
                .then(request.bodyToMono(UpdateTournamentStatusRequest.class)
                        .doOnNext(validatorHandler::validate))
                .flatMap(req -> updateTournamentStatusUseCase.execute(
                        tournamentId,
                        req.status()))
                .map(tournamentMapper::toTournamentResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }
}

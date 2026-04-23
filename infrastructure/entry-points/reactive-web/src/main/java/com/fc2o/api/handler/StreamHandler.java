package com.fc2o.api.handler;

import com.fc2o.api.dto.request.stream.CreateStreamRequest;
import com.fc2o.api.dto.request.stream.UpdateStreamRequest;
import com.fc2o.api.dto.response.generic.ApiResponse;
import com.fc2o.api.mapper.stream.StreamMapper;
import com.fc2o.api.security.DomainAuthorizationService;
import com.fc2o.api.validator.ValidatorHandler;
import com.fc2o.usecase.stream.CreateStreamUseCase;
import com.fc2o.usecase.stream.DeleteStreamUseCase;
import com.fc2o.usecase.stream.GetStreamUseCase;
import com.fc2o.usecase.stream.ListStreamsUseCase;
import com.fc2o.usecase.stream.UpdateStreamUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class StreamHandler {

    private final CreateStreamUseCase createStreamUseCase;
    private final GetStreamUseCase getStreamUseCase;
    private final ListStreamsUseCase listStreamsUseCase;
    private final UpdateStreamUseCase updateStreamUseCase;
    private final DeleteStreamUseCase deleteStreamUseCase;
    private final StreamMapper streamMapper;
    private final ValidatorHandler validatorHandler;
    private final DomainAuthorizationService authorizationService;

    @PreAuthorize("isAuthenticated()")
    public Mono<ServerResponse> getStream(ServerRequest request) {
        String streamId = request.pathVariable("id");
        return getStreamUseCase.execute(streamId)
                .flatMap(stream -> authorizationService.authorizeStreamAccess(stream.tournamentId()).thenReturn(stream))
                .map(streamMapper::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }

    @PreAuthorize("isAuthenticated()")
    public Mono<ServerResponse> listStreamsByTournament(ServerRequest request) {
        String tournamentId = request.pathVariable("tournamentId");
        return authorizationService.authorizeStreamAccess(tournamentId)
                .thenMany(listStreamsUseCase.executeByTournament(tournamentId))
                .map(streamMapper::toResponse)
                .collectList()
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }

    @PreAuthorize("hasAnyRole('PROMOTER','MOD','MODERATOR','ADMIN')")
    public Mono<ServerResponse> createStream(ServerRequest request) {
        return request.bodyToMono(CreateStreamRequest.class)
                .doOnNext(validatorHandler::validate)
                .flatMap(body -> authorizationService.authorizeTournamentManagement(body.tournamentId(), true)
                        .then(createStreamUseCase.execute(body.tournamentId(), body.platform(), body.url(), body.type())))
                .map(streamMapper::toResponse)
                .flatMap(response -> ServerResponse.created(URI.create("/api/v1/streams/" + response.id()))
                        .bodyValue(ApiResponse.created(response)));
    }

    @PreAuthorize("hasAnyRole('PROMOTER','MOD','MODERATOR','ADMIN')")
    public Mono<ServerResponse> updateStream(ServerRequest request) {
        String streamId = request.pathVariable("id");
        return getStreamUseCase.execute(streamId)
                .flatMap(stream -> authorizationService.authorizeTournamentManagement(stream.tournamentId(), true)
                        .then(request.bodyToMono(UpdateStreamRequest.class).doOnNext(validatorHandler::validate))
                        .flatMap(body -> updateStreamUseCase.execute(streamId, body.url(), body.type())))
                .map(streamMapper::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }

    @PreAuthorize("hasAnyRole('PROMOTER','MOD','MODERATOR','ADMIN')")
    public Mono<ServerResponse> deleteStream(ServerRequest request) {
        String streamId = request.pathVariable("id");
        return getStreamUseCase.execute(streamId)
                .flatMap(stream -> authorizationService.authorizeTournamentManagement(stream.tournamentId(), true)
                        .then(deleteStreamUseCase.execute(streamId)))
                .then(ServerResponse.noContent().build());
    }
}


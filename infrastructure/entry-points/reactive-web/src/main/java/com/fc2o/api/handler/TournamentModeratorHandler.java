package com.fc2o.api.handler;

import com.fc2o.api.dto.request.tournamentmoderator.AssignModeratorRequest;
import com.fc2o.api.dto.response.generic.ApiResponse;
import com.fc2o.api.mapper.tournamentmoderator.TournamentModeratorMapper;
import com.fc2o.api.security.DomainAuthorizationService;
import com.fc2o.api.validator.ValidatorHandler;
import com.fc2o.usecase.tournamentmoderator.AssignModeratorUseCase;
import com.fc2o.usecase.tournamentmoderator.GetModeratorsUseCase;
import com.fc2o.usecase.tournamentmoderator.RemoveModeratorUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class TournamentModeratorHandler {

    private final AssignModeratorUseCase assignModeratorUseCase;
    private final GetModeratorsUseCase getModeratorsUseCase;
    private final RemoveModeratorUseCase removeModeratorUseCase;
    private final TournamentModeratorMapper tournamentModeratorMapper;
    private final ValidatorHandler validatorHandler;
    private final DomainAuthorizationService authorizationService;

    @PreAuthorize("hasAnyRole('PROMOTER','MOD','MODERATOR','ADMIN')")
    public Mono<ServerResponse> listModerators(ServerRequest request) {
        String tournamentId = request.pathVariable("tournamentId");
        return authorizationService.authorizeTournamentManagement(tournamentId, true)
                .thenMany(getModeratorsUseCase.execute(tournamentId))
                .map(tournamentModeratorMapper::toResponse)
                .collectList()
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }

    @PreAuthorize("hasAnyRole('PROMOTER','ADMIN')")
    public Mono<ServerResponse> assignModerator(ServerRequest request) {
        return request.bodyToMono(AssignModeratorRequest.class)
                .doOnNext(validatorHandler::validate)
                .flatMap(body -> authorizationService.authorizeModeratorAssignment(body.tournamentId())
                        .then(assignModeratorUseCase.execute(body.tournamentId(), body.userId())))
                .map(tournamentModeratorMapper::toResponse)
                .flatMap(response -> ServerResponse.created(URI.create("/api/v1/tournament-moderators/" + response.id()))
                        .bodyValue(ApiResponse.created(response)));
    }

    @PreAuthorize("hasAnyRole('PROMOTER','MOD','MODERATOR','ADMIN')")
    public Mono<ServerResponse> removeModerator(ServerRequest request) {
        String tournamentId = request.pathVariable("tournamentId");
        String userId = request.pathVariable("userId");
        return authorizationService.authorizeModeratorRemoval(tournamentId, userId)
                .then(removeModeratorUseCase.execute(tournamentId, userId))
                .then(ServerResponse.noContent().build());
    }
}


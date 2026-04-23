package com.fc2o.api.handler;

import com.fc2o.api.config.JwtUserContextExtractor;
import com.fc2o.api.dto.request.team.AddTeamMemberRequest;
import com.fc2o.api.dto.request.team.CreateTeamRequest;
import com.fc2o.api.dto.request.team.UpdateTeamRequest;
import com.fc2o.api.dto.response.generic.ApiResponse;
import com.fc2o.api.mapper.team.TeamMapper;
import com.fc2o.api.security.DomainAuthorizationService;
import com.fc2o.api.validator.ValidatorHandler;
import com.fc2o.usecase.team.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class TeamHandler {
    private final CreateTeamUseCase createTeamUseCase;
    private final GetTeamUseCase getTeamUseCase;
    private final ListTeamsUseCase listTeamsUseCase;
    private final UpdateTeamUseCase updateTeamUseCase;
    private final DeleteTeamUseCase deleteTeamUseCase;
    private final GetTeamMembersUseCase getTeamMembersUseCase;
    private final AddTeamMemberUseCase addTeamMemberUseCase;
    private final RemoveTeamMemberUseCase removeTeamMemberUseCase;
    private final TeamMapper teamMapper;
    private final ValidatorHandler validatorHandler;
    private final JwtUserContextExtractor jwtUserContextExtractor;
    private final DomainAuthorizationService authorizationService;

    @PreAuthorize("hasAnyRole('PLAYER','ADMIN')")
    public Mono<ServerResponse> createTeam(ServerRequest request) {
        return jwtUserContextExtractor.extractUserId()
                .zipWith(request.bodyToMono(CreateTeamRequest.class).doOnNext(validatorHandler::validate))
                .flatMap(tuple -> createTeamUseCase.execute(tuple.getT2().name(), tuple.getT1()))
                .flatMap(team -> ServerResponse
                    .created(URI.create("/api/v1/teams/" + team.id()))
                    .bodyValue(ApiResponse.created(teamMapper.toResponse(team))));
    }

    @PreAuthorize("isAuthenticated()")
    public Mono<ServerResponse> getTeam(ServerRequest request) {
        String teamId = request.pathVariable("id");
        return getTeamUseCase.execute(teamId)
                .flatMap(team -> ServerResponse
                    .ok()
                    .bodyValue(ApiResponse.success(teamMapper.toResponse(team))));
    }

    @PreAuthorize("isAuthenticated()")
    public Mono<ServerResponse> listTeams(ServerRequest request) {
        return listTeamsUseCase.executeAll()
                .map(teamMapper::toResponse)
                .collectList()
                .flatMap(teams -> ServerResponse
                    .ok()
                    .bodyValue(ApiResponse.success(teams)));
    }

    @PreAuthorize("isAuthenticated()")
    public Mono<ServerResponse> listTeamsByLeader(ServerRequest request) {
        String leaderUserId = request.pathVariable("leaderUserId");
        return listTeamsUseCase.executeByLeader(leaderUserId)
                .map(teamMapper::toResponse)
                .collectList()
                .flatMap(teams -> ServerResponse.ok().bodyValue(ApiResponse.success(teams)));
    }

    @PreAuthorize("hasAnyRole('PLAYER','PROMOTER','ADMIN')")
    public Mono<ServerResponse> updateTeam(ServerRequest request) {
        String teamId = request.pathVariable("id");
        return authorizationService.authorizeTeamLeaderOrPrivileged(teamId)
                .then(request.bodyToMono(UpdateTeamRequest.class).doOnNext(validatorHandler::validate))
                .flatMap(body -> updateTeamUseCase.execute(teamId, body.name()))
                .map(teamMapper::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public Mono<ServerResponse> deleteTeam(ServerRequest request) {
        String teamId = request.pathVariable("id");
        return deleteTeamUseCase.execute(teamId)
                .then(ServerResponse.noContent().build());
    }

    @PreAuthorize("isAuthenticated()")
    public Mono<ServerResponse> listTeamMembers(ServerRequest request) {
        String teamId = request.pathVariable("id");
        return getTeamMembersUseCase.execute(teamId)
                .map(teamMapper::toTeamMemberResponse)
                .collectList()
                .flatMap(members -> ServerResponse.ok().bodyValue(ApiResponse.success(members)));
    }

    @PreAuthorize("hasAnyRole('PROMOTER','MOD','MODERATOR','ADMIN')")
    public Mono<ServerResponse> addTeamMember(ServerRequest request) {
        String teamId = request.pathVariable("id");
        return request.bodyToMono(AddTeamMemberRequest.class)
                .doOnNext(validatorHandler::validate)
                .flatMap(body -> addTeamMemberUseCase.execute(teamId, body.userId()))
                .map(teamMapper::toTeamMemberResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }

    @PreAuthorize("hasAnyRole('PROMOTER','MOD','MODERATOR','ADMIN')")
    public Mono<ServerResponse> removeTeamMember(ServerRequest request) {
        String teamId = request.pathVariable("id");
        String userId = request.pathVariable("userId");
        return removeTeamMemberUseCase.execute(teamId, userId)
                .then(ServerResponse.noContent().build());
    }
}


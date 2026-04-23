package com.fc2o.api.handler;

import com.fc2o.api.dto.request.userrole.AssignRoleRequest;
import com.fc2o.api.dto.response.generic.ApiResponse;
import com.fc2o.api.mapper.userrole.UserRoleMapper;
import com.fc2o.api.security.DomainAuthorizationService;
import com.fc2o.api.validator.ValidatorHandler;
import com.fc2o.usecase.userrole.AssignRoleUseCase;
import com.fc2o.usecase.userrole.GetUserRolesUseCase;
import com.fc2o.usecase.userrole.RemoveRoleUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class UserRoleHandler {

    private final AssignRoleUseCase assignRoleUseCase;
    private final GetUserRolesUseCase getUserRolesUseCase;
    private final RemoveRoleUseCase removeRoleUseCase;
    private final UserRoleMapper userRoleMapper;
    private final ValidatorHandler validatorHandler;
    private final DomainAuthorizationService authorizationService;

    @PreAuthorize("isAuthenticated()")
    public Mono<ServerResponse> getUserRoles(ServerRequest request) {
        String userId = request.pathVariable("userId");
        return authorizationService.authorizeSelfOrAdmin(userId)
                .thenMany(getUserRolesUseCase.execute(userId))
                .map(userRoleMapper::toResponse)
                .collectList()
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }

    @PreAuthorize("isAuthenticated()")
    public Mono<ServerResponse> assignRole(ServerRequest request) {
        return request.bodyToMono(AssignRoleRequest.class)
                .doOnNext(validatorHandler::validate)
                .flatMap(body -> authorizationService.authorizeRoleAssignment(body.userId(), body.role())
                        .then(assignRoleUseCase.execute(body.userId(), body.role())))
                .map(userRoleMapper::toResponse)
                .flatMap(response -> ServerResponse.created(URI.create("/api/v1/user-roles/" + response.id()))
                        .bodyValue(ApiResponse.created(response)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ServerResponse> removeRole(ServerRequest request) {
        return removeRoleUseCase.execute(request.pathVariable("id"))
                .then(ServerResponse.noContent().build());
    }
}


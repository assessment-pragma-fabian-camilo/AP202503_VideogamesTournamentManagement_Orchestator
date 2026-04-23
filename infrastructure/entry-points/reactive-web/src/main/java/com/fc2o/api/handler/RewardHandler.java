package com.fc2o.api.handler;

import com.fc2o.api.dto.request.reward.AssignRewardToTeamRequest;
import com.fc2o.api.dto.request.reward.CreateRewardRequest;
import com.fc2o.api.dto.request.reward.UpdateRewardRequest;
import com.fc2o.api.dto.response.generic.ApiResponse;
import com.fc2o.api.mapper.reward.RewardMapper;
import com.fc2o.api.security.DomainAuthorizationService;
import com.fc2o.api.validator.ValidatorHandler;
import com.fc2o.usecase.reward.AssignRewardToTeamUseCase;
import com.fc2o.usecase.reward.CreateRewardUseCase;
import com.fc2o.usecase.reward.GetRewardUseCase;
import com.fc2o.usecase.reward.ListRewardsUseCase;
import com.fc2o.usecase.reward.UpdateRewardUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class RewardHandler {

    private final CreateRewardUseCase createRewardUseCase;
    private final GetRewardUseCase getRewardUseCase;
    private final ListRewardsUseCase listRewardsUseCase;
    private final UpdateRewardUseCase updateRewardUseCase;
    private final AssignRewardToTeamUseCase assignRewardToTeamUseCase;
    private final RewardMapper rewardMapper;
    private final ValidatorHandler validatorHandler;
    private final DomainAuthorizationService authorizationService;

    @PreAuthorize("isAuthenticated()")
    public Mono<ServerResponse> getReward(ServerRequest request) {
        String rewardId = request.pathVariable("id");
        return getRewardUseCase.execute(rewardId)
                .map(rewardMapper::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }

    @PreAuthorize("isAuthenticated()")
    public Mono<ServerResponse> listRewardsByTournament(ServerRequest request) {
        String tournamentId = request.pathVariable("tournamentId");
        return listRewardsUseCase.executeByTournament(tournamentId)
                .map(rewardMapper::toResponse)
                .collectList()
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }

    @PreAuthorize("hasAnyRole('PROMOTER','ADMIN')")
    public Mono<ServerResponse> createReward(ServerRequest request) {
        return request.bodyToMono(CreateRewardRequest.class)
                .doOnNext(validatorHandler::validate)
                .flatMap(body -> authorizationService.authorizeTournamentManagement(body.tournamentId(), false)
                        .then(createRewardUseCase.execute(body.tournamentId(), body.position(), body.prize())))
                .map(rewardMapper::toResponse)
                .flatMap(response -> ServerResponse.created(URI.create("/api/v1/rewards/" + response.id()))
                        .bodyValue(ApiResponse.created(response)));
    }

    @PreAuthorize("hasAnyRole('PROMOTER','ADMIN')")
    public Mono<ServerResponse> updateReward(ServerRequest request) {
        String rewardId = request.pathVariable("id");
        return getRewardUseCase.execute(rewardId)
                .flatMap(reward -> authorizationService.authorizeTournamentManagement(reward.tournamentId(), false)
                        .then(request.bodyToMono(UpdateRewardRequest.class).doOnNext(validatorHandler::validate))
                        .flatMap(body -> updateRewardUseCase.execute(rewardId, body.prize())))
                .map(rewardMapper::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }

    @PreAuthorize("hasAnyRole('PROMOTER','ADMIN')")
    public Mono<ServerResponse> assignRewardToTeam(ServerRequest request) {
        String rewardId = request.pathVariable("id");
        return getRewardUseCase.execute(rewardId)
                .flatMap(reward -> authorizationService.authorizeTournamentManagement(reward.tournamentId(), false)
                        .then(request.bodyToMono(AssignRewardToTeamRequest.class).doOnNext(validatorHandler::validate))
                        .flatMap(body -> assignRewardToTeamUseCase.execute(rewardId, body.teamId())))
                .map(rewardMapper::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }
}


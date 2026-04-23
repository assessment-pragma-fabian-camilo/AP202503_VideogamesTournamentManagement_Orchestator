package com.fc2o.api.handler;

import com.fc2o.api.dto.request.commission.UpdateCommissionRequest;
import com.fc2o.api.dto.response.generic.ApiResponse;
import com.fc2o.api.mapper.commission.CommissionMapper;
import com.fc2o.api.validator.ValidatorHandler;
import com.fc2o.model.commission.Commission;
import com.fc2o.model.shared.CommissionType;
import com.fc2o.usecase.commission.GetCommissionUseCase;
import com.fc2o.usecase.commission.ListCommissionsUseCase;
import com.fc2o.usecase.commission.UpdateCommissionUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CommissionHandler {

    private final GetCommissionUseCase getCommissionUseCase;
    private final ListCommissionsUseCase listCommissionsUseCase;
    private final UpdateCommissionUseCase updateCommissionUseCase;
    private final CommissionMapper commissionMapper;
    private final ValidatorHandler validatorHandler;

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ServerResponse> getCommission(ServerRequest request) {
        return getCommissionUseCase.execute(CommissionType.valueOf(request.pathVariable("commissionType")))
                .map(commissionMapper::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ServerResponse> listCommissions(ServerRequest request) {
        return listCommissionsUseCase.execute()
                .map(commissionMapper::toResponse)
                .collectList()
                .flatMap(commissions -> ServerResponse.ok().bodyValue(ApiResponse.success(commissions)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ServerResponse> updateCommission(ServerRequest request) {
        return request.bodyToMono(UpdateCommissionRequest.class)
                .doOnNext(validatorHandler::validate)
                .map(body -> Commission.builder()
                        .commissionType(body.commissionType())
                        .participationPercentage(body.participationPercentage())
                        .visualizationPercentage(body.visualizationPercentage())
                        .donationPercentage(body.donationPercentage())
                        .build())
                .flatMap(updateCommissionUseCase::execute)
                .map(commissionMapper::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }
}


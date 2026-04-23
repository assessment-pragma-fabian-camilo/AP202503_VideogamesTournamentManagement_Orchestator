package com.fc2o.usecase.commission;

import com.fc2o.model.commission.Commission;
import com.fc2o.model.commission.gateways.CommissionRepository;
import com.fc2o.model.shared.CommissionType;
import reactor.core.publisher.Mono;

public class GetCommissionUseCase {
    private final CommissionRepository commissionRepository;

    public GetCommissionUseCase(CommissionRepository commissionRepository) {
        this.commissionRepository = commissionRepository;
    }

    public Mono<Commission> execute(CommissionType commissionType) {
        return commissionRepository.findByCommissionType(commissionType)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Commission not found for type: " + commissionType)));
    }
}


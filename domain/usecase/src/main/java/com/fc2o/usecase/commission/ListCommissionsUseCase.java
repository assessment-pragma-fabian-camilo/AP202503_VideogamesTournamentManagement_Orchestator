package com.fc2o.usecase.commission;

import com.fc2o.model.commission.Commission;
import com.fc2o.model.commission.gateways.CommissionRepository;
import com.fc2o.model.shared.CommissionType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ListCommissionsUseCase {
    private final CommissionRepository commissionRepository;

    public ListCommissionsUseCase(CommissionRepository commissionRepository) {
        this.commissionRepository = commissionRepository;
    }

    public Flux<Commission> execute() {
        return commissionRepository.findAll();
    }
}


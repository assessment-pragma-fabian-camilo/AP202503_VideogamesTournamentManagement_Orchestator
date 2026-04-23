package com.fc2o.usecase.commission;

import com.fc2o.model.commission.Commission;
import com.fc2o.model.commission.gateways.CommissionRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UpdateCommissionUseCase {
    private final CommissionRepository commissionRepository;

    /**
     * Update commission by type. Only users with ADMIN role can modify commissions.
     *
     * @param commission commission object
     * @return Updated commission
     */
    public Mono<Commission> execute(Commission commission) {
        return commissionRepository.findByCommissionType(commission.commissionType())
                .flatMap(currentCommission -> {
                    Commission updatedCommission = currentCommission.toBuilder()
                            .participationPercentage(commission.participationPercentage())
                            .visualizationPercentage(commission.visualizationPercentage())
                            .donationPercentage(commission.donationPercentage())
                            .build();
                    return commissionRepository.update(updatedCommission);
                })
                .switchIfEmpty(Mono.error(new IllegalArgumentException(
                        "Commission not found for type: " + commission.commissionType())));
    }
}


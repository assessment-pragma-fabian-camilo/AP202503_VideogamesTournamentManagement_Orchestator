package com.fc2o.model.commission.gateways;

import com.fc2o.model.commission.Commission;
import com.fc2o.model.shared.CommissionType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Gateway interface for Commission repository operations.
 * Defines all operations needed by commission use cases.
 */
public interface CommissionRepository {
    /**
     * Find commission by type (FREE or PAID).
     */
    Mono<Commission> findByCommissionType(CommissionType commissionType);

    /**
     * Find all commissions.
     */
    Flux<Commission> findAll();

    /**
     * Update commission.
     */
    Mono<Commission> update(Commission commission);
}


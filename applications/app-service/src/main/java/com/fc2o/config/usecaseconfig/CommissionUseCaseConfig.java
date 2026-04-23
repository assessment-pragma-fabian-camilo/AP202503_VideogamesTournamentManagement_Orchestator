package com.fc2o.config.usecaseconfig;

import com.fc2o.model.commission.gateways.CommissionRepository;
import com.fc2o.usecase.commission.GetCommissionUseCase;
import com.fc2o.usecase.commission.ListCommissionsUseCase;
import com.fc2o.usecase.commission.UpdateCommissionUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Commission use cases.
 */
@Configuration
public class CommissionUseCaseConfig {

    @Bean
    public GetCommissionUseCase getCommissionUseCase(
            CommissionRepository commissionRepository) {
        return new GetCommissionUseCase(commissionRepository);
    }

    @Bean
    public ListCommissionsUseCase listCommissionsUseCase(
            CommissionRepository commissionRepository) {
        return new ListCommissionsUseCase(commissionRepository);
    }

    @Bean
    public UpdateCommissionUseCase updateCommissionUseCase(
            CommissionRepository commissionRepository) {
        return new UpdateCommissionUseCase(commissionRepository);
    }
}


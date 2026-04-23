package com.fc2o.model.commission;

import com.fc2o.model.shared.CommissionType;
import lombok.Builder;

@Builder(toBuilder = true)
public record Commission(
        String id,
        String createdAt,
        CommissionType commissionType,
        Float participationPercentage,
        Float visualizationPercentage,
        Float donationPercentage
) {
    public boolean isFree() {
        return commissionType == CommissionType.FREE;
    }

    public boolean isPaid() {
        return commissionType == CommissionType.PAID;
    }
}


package com.fc2o.api.dto.response.commission;

import com.fc2o.model.shared.CommissionType;

public record CommissionResponse(
        String id,
        String createdAt,
        CommissionType commissionType,
        Float participationPercentage,
        Float visualizationPercentage,
        Float donationPercentage
) {
}


package com.fc2o.supabase.commission.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CommissionRowDto(
        String id,
        @JsonProperty("created_at")
        String createdAt,
        @JsonProperty("commission_type")
        String commissionType,
        @JsonProperty("participation_percentage")
        Float participationPercentage,
        @JsonProperty("visualization_percentage")
        Float visualizationPercentage,
        @JsonProperty("donation_percentage")
        Float donationPercentage
) {
}


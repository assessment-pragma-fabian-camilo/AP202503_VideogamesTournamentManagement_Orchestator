package com.fc2o.supabase.donation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record DonationRowDto(
        String id,
        @JsonProperty("createdAt")
        String createdAt,
        @JsonProperty("tournament_id")
        String tournamentId,
        @JsonProperty("user_id")
        String userId,
        @JsonProperty("team_id")
        String teamId,
        Double amount,
        String message,
        String status,
        @JsonProperty("payment_method")
        String paymentMethod
) {
}


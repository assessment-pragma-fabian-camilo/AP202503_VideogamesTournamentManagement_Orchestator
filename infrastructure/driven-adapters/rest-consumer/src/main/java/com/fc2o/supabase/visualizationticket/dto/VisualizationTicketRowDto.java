package com.fc2o.supabase.visualizationticket.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record VisualizationTicketRowDto(
        String id,
        @JsonProperty("created_at")
        String createdAt,
        @JsonProperty("tournament_id")
        String tournamentId,
        @JsonProperty("user_id")
        String userId,
        byte[] qr,
        @JsonProperty("transaction_id")
        String transactionStatus,
        @JsonProperty("ticket_status")
        String ticketStatus,
        @JsonProperty("payment_method")
        String paymentMethod,
        Double amount
) {
}


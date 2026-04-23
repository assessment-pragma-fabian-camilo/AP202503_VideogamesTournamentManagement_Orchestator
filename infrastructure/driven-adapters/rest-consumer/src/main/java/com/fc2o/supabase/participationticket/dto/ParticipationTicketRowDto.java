package com.fc2o.supabase.participationticket.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ParticipationTicketRowDto(
        String id,
        @JsonProperty("created_at")
        String createdAt,
        @JsonProperty("tournament_id")
        String tournamentId,
        @JsonProperty("user_id")
        String userId,
        @JsonProperty("team_id")
        String teamId,
        byte[] qr,
        @JsonProperty("transaction_status")
        String transactionStatus,
        @JsonProperty("ticket_status")
        String ticketStatus,
        @JsonProperty("payment_method")
        String paymentMethod,
        Double amount
) {
}
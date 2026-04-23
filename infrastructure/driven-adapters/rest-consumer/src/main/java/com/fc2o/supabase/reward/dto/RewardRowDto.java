package com.fc2o.supabase.reward.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RewardRowDto(
        String id,
        @JsonProperty("created_at")
        String createdAt,
        @JsonProperty("tournament_id")
        String tournamentId,
        @JsonProperty("team_id")
        String teamId,
        Short position,
        Double prize
) {
}


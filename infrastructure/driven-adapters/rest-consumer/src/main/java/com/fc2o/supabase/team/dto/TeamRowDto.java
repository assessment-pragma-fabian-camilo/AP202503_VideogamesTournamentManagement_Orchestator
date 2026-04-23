package com.fc2o.supabase.team.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record TeamRowDto(
        String id,
        @JsonProperty("created_at")
        String createdAt,
        String name,
        @JsonProperty("leader_user_id")
        String leaderUserId
) {
}


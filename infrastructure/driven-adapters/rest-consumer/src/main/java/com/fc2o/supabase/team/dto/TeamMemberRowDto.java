package com.fc2o.supabase.team.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record TeamMemberRowDto(
        String id,
        @JsonProperty("created_at")
        String createdAt,
        @JsonProperty("team_id")
        String teamId,
        @JsonProperty("user_id")
        String userId
) {
}


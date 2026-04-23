package com.fc2o.supabase.match.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record MatchTeamRowDto(
        String id,
        @JsonProperty("created_at")
        String createdAt,
        @JsonProperty("match_id")
        String matchId,
        @JsonProperty("team_id")
        String teamId
) {
}


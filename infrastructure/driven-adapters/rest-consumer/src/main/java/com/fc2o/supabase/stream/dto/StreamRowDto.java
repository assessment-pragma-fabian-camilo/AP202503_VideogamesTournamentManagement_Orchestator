package com.fc2o.supabase.stream.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record StreamRowDto(
        String id,
        @JsonProperty("created_at")
        String createdAt,
        @JsonProperty("tournament_id")
        String tournamentId,
        String platform,
        String url,
        String type
) {
}


package com.fc2o.supabase.match.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record MatchRowDto(
        String id,
        @JsonProperty("created_at")
        String createdAt,
        @JsonProperty("tournament_id")
        String tournamentId,
        @JsonProperty("winner_team_id")
        String winnerTeamId,
        @JsonProperty("start_datetime")
        String startDatetime,
        @JsonProperty("end_datetime")
        String endDatetime,
        String status,
        @JsonProperty("match_details")
        String matchDetails
) {
}


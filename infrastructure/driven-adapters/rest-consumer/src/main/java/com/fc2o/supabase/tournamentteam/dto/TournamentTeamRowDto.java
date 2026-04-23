package com.fc2o.supabase.tournamentteam.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for tournament_teams table row from Supabase.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TournamentTeamRowDto {
    private String id;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("tournament_id")
    private String tournamentId;
    @JsonProperty("team_id")
    private String teamId;
}


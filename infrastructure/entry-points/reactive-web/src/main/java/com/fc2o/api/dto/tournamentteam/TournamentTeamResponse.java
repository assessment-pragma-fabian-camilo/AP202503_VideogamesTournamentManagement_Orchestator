package com.fc2o.api.dto.tournamentteam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO response for tournament team operations.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TournamentTeamResponse {
    private String id;
    private String createdAt;
    private String tournamentId;
    private String teamId;
}


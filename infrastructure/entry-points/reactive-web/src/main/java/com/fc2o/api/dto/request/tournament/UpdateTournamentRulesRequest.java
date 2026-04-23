package com.fc2o.api.dto.request.tournament;

/**
 * Request DTO for updating tournament rules and description.
 * Both fields are optional; null means "no change".
 */
public record UpdateTournamentRulesRequest(
        String rules,
        String description
) {}


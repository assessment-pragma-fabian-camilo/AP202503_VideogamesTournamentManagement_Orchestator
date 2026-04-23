package com.fc2o.api.dto.request.tournament;

/**
 * Request DTO for updating basic tournament information (name, dates).
 * All fields are optional; null means "no change".
 */
public record UpdateTournamentRequest(
        String name,
        String dateStart,
        String dateEnd
) {}


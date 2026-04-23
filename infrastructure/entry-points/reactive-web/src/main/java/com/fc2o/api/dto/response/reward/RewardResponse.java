package com.fc2o.api.dto.response.reward;

/**
 * Response DTO for reward.
 */
public record RewardResponse(
    String id,
    String tournamentId,
    String teamId,
    Short position,
    Double prize,
    String createdAt
) {}


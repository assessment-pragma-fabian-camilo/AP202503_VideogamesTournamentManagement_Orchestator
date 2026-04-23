package com.fc2o.supabase.tournament.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record TournamentRowDto(
        String id,
        @JsonProperty("createdAt")
        String createdAt,
        String name,
        String rules,
        @JsonProperty("promoter_user_id")
        String promoterUserId,
        @JsonProperty("game_id")
        String gameId,
        @JsonProperty("place_limit")
        Short placeLimit,
        @JsonProperty("place_remaining")
        Short placeRemaining,
        @JsonProperty("place_minimum")
        Short placeMinimum,
        @JsonProperty("date_start")
        String dateStart,
        @JsonProperty("date_end")
        String dateEnd,
        @JsonProperty("participation_price")
        Double participationPrice,
        @JsonProperty("visualization_price")
        Double visualizationPrice,
        String type,
        String status,
        String description
) {
}


package com.fc2o.supabase.userrole.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserRoleRowDto(
        String id,
        @JsonProperty("created_at")
        String created_at,
        @JsonProperty("user_id")
        String user_id,
        String role
) {
}


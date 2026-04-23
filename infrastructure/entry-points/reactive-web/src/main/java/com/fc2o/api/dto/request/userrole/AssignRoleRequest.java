package com.fc2o.api.dto.request.userrole;

import com.fc2o.model.userrole.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AssignRoleRequest(
        @NotBlank(message = "User id is required")
        String userId,

        @NotNull(message = "Role is required")
        Role role
) {
}


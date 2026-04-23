package com.fc2o.api.dto.response.userrole;

import com.fc2o.model.userrole.Role;

public record UserRoleResponse(
        String id,
        String createdAt,
        String userId,
        Role role
) {
}


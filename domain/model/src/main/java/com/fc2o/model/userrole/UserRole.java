package com.fc2o.model.userrole;

import lombok.Builder;

@Builder(toBuilder = true)
public record UserRole(
        String id,
        String createdAt,
        String userId,
        Role role
) {

    public boolean isAdmin() {
        return Role.ADMIN.equals(role);
    }

    public boolean isPromoter() {
        return Role.PROMOTER.equals(role);
    }

    public boolean isPlayer() {
        return Role.PLAYER.equals(role);
    }

    public boolean isViewer() {
        return Role.VIEWER.equals(role);
    }

    public boolean isModerator() {
        return Role.MOD.equals(role);
    }
}


package com.fc2o.model.user;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Builder(toBuilder = true)
public record User(
  UUID id,
  LocalDateTime createdTime,
  String alias,
  String name,
  String email,
  String phone,
  LocalDate birthDate,
  Status status,
  String password,
  Set<Role> roles
) {
  public Boolean isPromoter() {
    return this.roles().stream().anyMatch(role -> role.equals(Role.PROMOTER));
  }

  public Boolean isAdministrator() {
    return this.roles().stream().anyMatch(role -> role.equals(Role.ADMINISTRATOR));
  }

  public Boolean isParticipant() {
    return this.roles().stream().anyMatch(role -> role.equals(Role.PARTICIPANT));
  }

  public Boolean isViewer() {
    return this.roles().stream().anyMatch(role -> role.equals(Role.VIEWER));
  }

  public Boolean isActive() {
    return status().equals(Status.ACTIVE);
  }

  public Boolean isBaned() {
    return status().equals(Status.BANED);
  }
}

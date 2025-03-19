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
}

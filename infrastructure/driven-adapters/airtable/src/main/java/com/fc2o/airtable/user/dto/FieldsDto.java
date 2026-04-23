package com.fc2o.airtable.user.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.Set;

@Builder
public record FieldsDto(
  String name,
  String email,
  String phone,
  LocalDate birthDate,
  String password,
  StatusDto status,
  String alias,
  Set<RoleDto> roles
) {
}

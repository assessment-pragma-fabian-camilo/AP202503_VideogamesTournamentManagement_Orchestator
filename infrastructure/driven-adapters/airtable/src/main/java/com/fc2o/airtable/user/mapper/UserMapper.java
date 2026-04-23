package com.fc2o.airtable.user.mapper;

import com.fc2o.airtable.user.dto.*;
import com.fc2o.model.user.Role;
import com.fc2o.model.user.Status;
import com.fc2o.model.user.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

  public User toUser(RecordDto dto) {
    return User.builder()
      .createdTime(dto.createdTime())
      .id(dto.id())
      .alias(dto.fields().alias())
      .name(dto.fields().name())
      .email(dto.fields().email())
      .phone(dto.fields().phone())
      .birthDate(dto.fields().birthDate())
      .status(Status.valueOf(dto.fields().status().toString()))
      .password(dto.fields().password())
      .roles(
        dto.fields().roles().stream()
          .map(role -> Role.valueOf(role.toString())).collect(Collectors.toSet())
      )
      .build();
  }

  public WrapperDto toWrapperDto(User user) {
    return WrapperDto.builder()
      .records(
        List.of(
          RecordDto.builder()
            .id(user.id())
            .fields(
              FieldsDto.builder()
                .alias(user.alias())
                .name(user.name())
                .email(user.email())
                .phone(user.phone())
                .birthDate(user.birthDate())
                .status(StatusDto.valueOf(user.status().name()))
                .password(user.password())
                .roles(user.roles().stream().map(role -> RoleDto.valueOf(role.name())).collect(Collectors.toSet()))
                .build()
            )
            .build()
        )
      )
      .build();
  }
}

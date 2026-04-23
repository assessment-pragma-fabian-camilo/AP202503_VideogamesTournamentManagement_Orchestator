package com.fc2o.airtable.registration.mapper;

import com.fc2o.airtable.registration.dto.FieldsDto;
import com.fc2o.airtable.registration.dto.RecordDto;
import com.fc2o.airtable.registration.dto.StatusDto;
import com.fc2o.airtable.registration.dto.WrapperDto;
import com.fc2o.model.registration.Registration;
import com.fc2o.model.registration.Status;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RegistrationMapper {

  public Registration toRegistration(RecordDto dto) {
    return Registration.builder()
      .createdTime(dto.createdTime())
      .id(dto.id())
      .tournamentId(dto.fields().tournamentId())
      .participantId(dto.fields().participantId())
      .status(Status.valueOf(dto.fields().status().name()))
      .build();
  }

  public WrapperDto toWrapperDto(Registration registration) {
    return WrapperDto.builder()
      .records(
        List.of(
          RecordDto.builder()
            .id(registration.id())
            .fields(
              FieldsDto.builder()
                .tournamentId(registration.tournamentId())
                .participantId(registration.participantId())
                .status(StatusDto.valueOf(registration.status().name()))
                .build()
            )
            .build()
        )
      )
      .build();
  }
}

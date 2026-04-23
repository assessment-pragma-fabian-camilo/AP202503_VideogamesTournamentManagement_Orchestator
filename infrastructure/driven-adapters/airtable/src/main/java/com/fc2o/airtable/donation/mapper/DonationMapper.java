package com.fc2o.airtable.donation.mapper;

import com.fc2o.airtable.donation.dto.FieldsDto;
import com.fc2o.airtable.donation.dto.RecordDto;
import com.fc2o.airtable.donation.dto.WrapperDto;
import com.fc2o.model.donation.Donation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DonationMapper {

  public Donation toDonation(RecordDto dto) {
    return Donation.builder()
      .createdTime(dto.createdTime())
      .id(dto.id())
      .userId(dto.fields().userId())
      .tournamentId(dto.fields().tournamentId())
      .amount(dto.fields().amount())
      .build();
  }

  public WrapperDto toWrapperDto(Donation donation) {
    return WrapperDto.builder()
      .records(
        List.of(
          RecordDto.builder()
            .id(donation.id())
            .fields(
              FieldsDto.builder()
                .userId(donation.userId())
                .tournamentId(donation.tournamentId())
                .amount(donation.amount())
                .build()
            )
            .build()
        )
      )
      .build();
  }
}

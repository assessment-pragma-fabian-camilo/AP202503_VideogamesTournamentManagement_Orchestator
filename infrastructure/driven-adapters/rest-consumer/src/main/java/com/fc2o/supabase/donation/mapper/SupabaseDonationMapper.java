package com.fc2o.supabase.donation.mapper;

import com.fc2o.model.donation.Donation;
import com.fc2o.supabase.donation.dto.DonationRowDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface SupabaseDonationMapper {

    DonationRowDto toDto(Donation domain);

    Donation toDomain(DonationRowDto dto);
}
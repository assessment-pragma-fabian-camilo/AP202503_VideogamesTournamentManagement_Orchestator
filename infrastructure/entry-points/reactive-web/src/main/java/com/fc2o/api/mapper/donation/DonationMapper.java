package com.fc2o.api.mapper.donation;

import com.fc2o.api.dto.request.donation.CreateDonationRequest;
import com.fc2o.api.dto.response.donation.DonationResponse;
import com.fc2o.model.donation.Donation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface DonationMapper {

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "tournamentId", source = "dto.tournamentId")
    @Mapping(target = "teamId", source = "dto.teamId")
    @Mapping(target = "amount", source = "dto.amount")
    @Mapping(target = "message", source = "dto.message")
    Donation toDomain(String userId, CreateDonationRequest dto);

    DonationResponse toResponse(Donation donation);
}


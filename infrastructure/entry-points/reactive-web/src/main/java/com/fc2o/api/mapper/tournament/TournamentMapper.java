package com.fc2o.api.mapper.tournament;

import com.fc2o.api.dto.request.tournament.CreateTournamentRequest;
import com.fc2o.api.dto.response.tournament.TournamentResponse;
import com.fc2o.model.tournament.Tournament;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface TournamentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "promoterUserId", ignore = true)
    @Mapping(source = "placeLimit", target = "placeRemaining")
    @Mapping(target = "status", expression = "java(com.fc2o.model.shared.TournamentStatus.UPCOMING)")
    Tournament toTournamentFromCreateRequest(CreateTournamentRequest request);

    TournamentResponse toTournamentResponse(Tournament tournament);
}

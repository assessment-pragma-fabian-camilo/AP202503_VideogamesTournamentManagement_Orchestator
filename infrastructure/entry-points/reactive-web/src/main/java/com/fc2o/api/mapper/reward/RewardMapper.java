package com.fc2o.api.mapper.reward;

import com.fc2o.api.dto.response.reward.RewardResponse;
import com.fc2o.model.reward.Reward;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface RewardMapper {

    RewardResponse toResponse(Reward reward);
}


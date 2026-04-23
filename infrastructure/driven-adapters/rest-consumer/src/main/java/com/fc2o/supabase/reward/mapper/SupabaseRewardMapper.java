package com.fc2o.supabase.reward.mapper;

import com.fc2o.model.reward.Reward;
import com.fc2o.supabase.reward.dto.RewardRowDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface SupabaseRewardMapper {

    RewardRowDto toDto(Reward domain);

    Reward toDomain(RewardRowDto dto);
}
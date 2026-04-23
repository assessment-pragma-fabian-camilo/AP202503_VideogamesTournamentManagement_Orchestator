package com.fc2o.supabase.commission.mapper;

import com.fc2o.model.commission.Commission;
import com.fc2o.supabase.commission.dto.CommissionRowDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface SupabaseCommissionMapper {

    CommissionRowDto toDto(Commission domain);

    Commission toDomain(CommissionRowDto dto);
}
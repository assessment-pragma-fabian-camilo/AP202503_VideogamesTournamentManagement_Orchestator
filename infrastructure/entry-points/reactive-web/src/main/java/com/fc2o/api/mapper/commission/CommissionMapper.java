package com.fc2o.api.mapper.commission;

import com.fc2o.api.dto.response.commission.CommissionResponse;
import com.fc2o.model.commission.Commission;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface CommissionMapper {

    CommissionResponse toResponse(Commission commission);
}


package com.fc2o.api.mapper.userrole;

import com.fc2o.api.dto.response.userrole.UserRoleResponse;
import com.fc2o.model.userrole.UserRole;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface UserRoleMapper {

    UserRoleResponse toResponse(UserRole userRole);
}


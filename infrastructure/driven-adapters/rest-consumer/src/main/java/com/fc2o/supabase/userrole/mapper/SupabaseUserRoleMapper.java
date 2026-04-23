package com.fc2o.supabase.userrole.mapper;

import com.fc2o.model.userrole.UserRole;
import com.fc2o.supabase.userrole.dto.UserRoleRowDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface SupabaseUserRoleMapper {

    UserRoleRowDto toDto(UserRole domain);

    UserRole toDomain(UserRoleRowDto dto);
}


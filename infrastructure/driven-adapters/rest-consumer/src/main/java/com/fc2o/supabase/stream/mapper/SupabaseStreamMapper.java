package com.fc2o.supabase.stream.mapper;

import com.fc2o.model.stream.Stream;
import com.fc2o.supabase.stream.dto.StreamRowDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface SupabaseStreamMapper {

    StreamRowDto toDto(Stream domain);

    Stream toDomain(StreamRowDto dto);
}
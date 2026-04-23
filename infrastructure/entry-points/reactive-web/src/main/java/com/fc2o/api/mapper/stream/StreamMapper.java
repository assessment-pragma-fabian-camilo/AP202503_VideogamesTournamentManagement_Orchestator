package com.fc2o.api.mapper.stream;

import com.fc2o.api.dto.response.stream.StreamResponse;
import com.fc2o.model.stream.Stream;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface StreamMapper {

    StreamResponse toResponse(Stream stream);
}


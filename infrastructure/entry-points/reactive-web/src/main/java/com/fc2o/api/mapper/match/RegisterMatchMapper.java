package com.fc2o.api.mapper.match;

import com.fc2o.api.dto.match.req.register.RegisterMatchRequestDto;
import com.fc2o.api.dto.match.res.register.RegisterMatchResponseDto;
import com.fc2o.model.match.Match;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface RegisterMatchMapper {
  @Mapping(source = "match", target = ".")
  Match toMatch(RegisterMatchRequestDto dto);

  @Mapping(target = "match")
  RegisterMatchResponseDto toRegisterMatchResponseDto(Match match);
}

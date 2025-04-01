package com.fc2o.emailsender.mapper;

import com.fc2o.emailsender.dto.EmailMessageDto;
import com.fc2o.model.notificationmessage.NotificationMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface EmailMessageMapper {
  @Mapping(target = ".", source = ".")
  EmailMessageDto toEmailMessageDto(NotificationMessage message);
}

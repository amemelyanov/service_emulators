package org.examples.server.mapper;

import org.examples.server.dto.MessageDto;
import org.examples.server.model.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapstructMapper {
    @Mapping(target = "msgId", source = "msg_id")
    @Mapping(target = "timeRq", source = "timestamp")
    Message getEntityFromDto(MessageDto messageDto);
}

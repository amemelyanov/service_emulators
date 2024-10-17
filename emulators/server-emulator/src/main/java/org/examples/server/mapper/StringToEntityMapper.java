package org.examples.server.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.examples.server.dto.MessageDto;
import org.examples.server.model.Message;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StringToEntityMapper {

    private final MessageMapstructMapper messageMapper;
    private final ObjectMapper objectMapper;

    public Message getEntityFromString(String stringMessage) throws JsonProcessingException {
        MessageDto dtoFromString = getDtoFromString(stringMessage);
        return messageMapper.getEntityFromDto(dtoFromString);
    }

    private MessageDto getDtoFromString(String stringMessage) throws JsonProcessingException {
        return objectMapper.readValue(stringMessage, MessageDto.class);
    }
}
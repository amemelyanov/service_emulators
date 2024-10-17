package org.examples.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.examples.server.dto.MessageDto;
import org.examples.server.mapper.MessageMapstructMapper;
import org.examples.server.mapper.StringToEntityMapper;
import org.examples.server.model.Message;
import org.examples.server.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    @Value("${spring.kafka.topic}")
    private String topic;

    private final MessageRepository messageRepository;
    private final MessageMapstructMapper messageMapstructMapper;
    private final StringToEntityMapper stringToEntityMapper;

    @KafkaListener(topics = "${spring.kafka.topic}", groupId = "message-group",
            containerFactory = "messageDtoKafkaListenerContainerFactory")
    public void receiveMessage(MessageDto messageDto) {
        Message message = messageMapstructMapper.getEntityFromDto(messageDto);
        messageRepository.save(message);
        log.info("Из Kafka получен объект: {}, topic: {}", messageDto.toString(), topic);
    }

    @KafkaListener(topics = "jmeter_msg", groupId = "message-group",
            containerFactory = "stringMessageKafkaListenerContainerFactory")
    public void receiveMessage(String stringMessage) throws JsonProcessingException {
        Message message = stringToEntityMapper.getEntityFromString(stringMessage);
        messageRepository.save(message);
        log.info("Из Kafka получен объект: {}, topic: {}", stringMessage, topic);
    }
}

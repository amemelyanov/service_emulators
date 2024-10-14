package org.examples.rest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.examples.rest.model.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    @Value("${spring.kafka.topic}")
    private String topic;

    private final KafkaTemplate<String, Message> kafkaTemplate;

    public void save(Message message) throws ExecutionException, InterruptedException, TimeoutException {
        kafkaTemplate.send(topic, message);
        log.info("Вызов метода save() класса MessageService");
    }
}

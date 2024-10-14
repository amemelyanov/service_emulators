package org.examples.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.examples.rest.model.Message;
import org.examples.rest.service.MessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final ObjectMapper objectMapper;
    @Value("${message.id.name}")
    private String idName;

    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping("/post-message")
    public void save(@RequestBody Map<String, String> body) throws Exception {
        var id = body.get(idName);
        if (id == null) {
            throw new IllegalArgumentException("Invalid data in request");
        }
        var message = Message.builder()
                .msg_id(id)
                .timestamp(String.valueOf(Instant.now().toEpochMilli()))
                .method("POST")
                .uri("/post-message")
                .build();
        messageService.save(message);
        log.info("Вызов метода save() класса MessageController");
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public void illegalArgumentException(Exception e, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {
            {
                put("message", e.getMessage());
                put("type", e.getClass());
            }
        }));
        log.error(e.getLocalizedMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    public void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {
            {
                put("message", e.getMessage());
                put("type", e.getClass());
            }
        }));
        log.error(e.getLocalizedMessage());
    }
}

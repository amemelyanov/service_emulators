package org.examples.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageDto {

    private String msg_id;
    private String timestamp;
    private String method;
    private String uri;
}
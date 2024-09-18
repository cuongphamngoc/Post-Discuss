package com.cuongpn.dto.responeDTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageResponseDTO {
    private Long id;
    private String username;
    private String avatarUrl;
    private LocalDateTime timestamp;
    private String text;
    private String type;
    private MessageReplyTo messageReplyTo;
    @Data
    public static class MessageReplyTo{
        private Long id;
        private String username;
        private String text;
        private String type;
    }
}

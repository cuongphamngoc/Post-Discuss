package com.cuongpn.dto.requestDTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChatMessage {
    private String senderName;
    private String senderAvatarUrl;
    private String content;
    private MessageType type;
    private Long repliedMessageId;

    public enum MessageType {
        TEXT,
        IMAGE,
        VIDEO,
        AUDIO
    }

}

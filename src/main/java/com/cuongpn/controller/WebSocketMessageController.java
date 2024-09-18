package com.cuongpn.controller;

import com.cuongpn.dto.requestDTO.ChatMessage;
import com.cuongpn.dto.responeDTO.MessageResponseDTO;
import com.cuongpn.entity.Message;
import com.cuongpn.entity.User;

import com.cuongpn.mapper.MessageMapper;
import com.cuongpn.service.MessageService;
import com.cuongpn.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.Objects;

@Controller
@AllArgsConstructor
public class WebSocketMessageController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;
    private final UserService userService;
    private final MessageMapper mapper;

    @MessageMapping("/chat")
    public void sendMessage(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {

        // Tạo đối tượng Message từ ChatMessage
        Message message = new Message();
        message.setContent(chatMessage.getContent());
        message.setTimestamp(LocalDateTime.now());

        // Lấy thông tin người gửi từ header
        String username = Objects.requireNonNull(headerAccessor.getUser()).getName();
        User sender = userService.getUserByMail(username);
        message.setSender(sender);
        System.out.println(chatMessage.getType().toString());
        message.setType(chatMessage.getType().toString());


        // Xử lý nếu có tin nhắn được trả lời
        if (chatMessage.getRepliedMessageId() != null) {
            Message repliedMessage = messageService.findById(chatMessage.getRepliedMessageId()).orElse(null);
            message.setReplied(repliedMessage);
        }

        // Lưu tin nhắn vào database
        Message savedMessage = messageService.saveMessage(message);
        MessageResponseDTO messageResponseDTO = mapper.messageToMessageResponseDTO(savedMessage);
        // Gửi tin nhắn dưới dạng JSON cho tất cả các client đã đăng ký vào "/topic/chat"
        messagingTemplate.convertAndSend("/topic/chat",messageResponseDTO);
    }


}

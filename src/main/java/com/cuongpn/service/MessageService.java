package com.cuongpn.service;

import com.cuongpn.dto.responeDTO.MessageResponseDTO;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.entity.Message;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    Message saveMessage(Message message);

    ResponseData<?> loadMessages(Long current);

    Optional<Message> findById(Long id);

    ResponseData<List<MessageResponseDTO>> findAll();

    ResponseData<List<MessageResponseDTO>> getMessagesFromReplyTo(Long replyToId);
}

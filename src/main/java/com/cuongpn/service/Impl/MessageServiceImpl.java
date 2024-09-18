package com.cuongpn.service.Impl;

import com.cuongpn.dto.responeDTO.MessageResponseDTO;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.entity.Message;
import com.cuongpn.mapper.MessageMapper;
import com.cuongpn.repository.ChatRepository;
import com.cuongpn.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final ChatRepository chatRepository;
    private final MessageMapper mapper;
    @Override
    public Message saveMessage(Message message) {
        return chatRepository.save(message);
    }

    @Override
    public ResponseData<?> loadMessages(Long current) {
        List<Message> res  = chatRepository.findByOrderByTimestampDesc();
        return new ResponseData<>(HttpStatus.OK.value(), "Load message success",res);
    }

    @Override
    public Optional<Message> findById(Long id) {
        return Optional.ofNullable(chatRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Message id not found")));
    }

    @Override
    public ResponseData<List<MessageResponseDTO>> findAll() {
        List<Message> list = chatRepository.findAll();
        List<MessageResponseDTO> res = list.stream().map((mapper::messageToMessageResponseDTO)).toList();
        return new ResponseData<>(HttpStatus.OK.value(), "Get Message success",res);
    }

    @Override
    public ResponseData<List<MessageResponseDTO>> getMessagesFromReplyTo(Long replyToId) {
        LocalDateTime replyTime = chatRepository.findById(replyToId)
                .map(Message::getTimestamp)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        List<Message> list = chatRepository.findMessagesFromReplyTime(replyTime, replyToId);
        return new ResponseData<>(HttpStatus.OK.value(), "Get Message success",list.stream().map(mapper::messageToMessageResponseDTO).toList());
    }
}

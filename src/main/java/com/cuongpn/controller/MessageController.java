package com.cuongpn.controller;

import com.cuongpn.dto.responeDTO.MessageResponseDTO;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/message")
public class MessageController {
    private final MessageService messageService;

    @GetMapping
    public ResponseData<List<MessageResponseDTO>> getAll(){
        return messageService.findAll();
    }
    @GetMapping("/reply/{id}")
    public ResponseData<List<MessageResponseDTO>> getMessagesWithRepliedId(@PathVariable("id") Long RepliedId){
        return messageService.getMessagesFromReplyTo(RepliedId);
    }
}

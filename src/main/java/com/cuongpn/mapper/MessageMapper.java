package com.cuongpn.mapper;

import com.cuongpn.dto.responeDTO.MessageResponseDTO;
import com.cuongpn.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    @Mapping(source = "sender.name", target = "username")
    @Mapping(source = "sender.avatarUrl", target = "avatarUrl")
    @Mapping(source = "content", target = "text")
    @Mapping(source = "replied.id", target = "messageReplyTo.id")
    @Mapping(source = "replied.sender.name", target = "messageReplyTo.username")
    @Mapping(source = "replied.content", target = "messageReplyTo.text")
    @Mapping(source = "type" ,target = "type")
    @Mapping(source = "replied.type", target = "messageReplyTo.type")
    MessageResponseDTO messageToMessageResponseDTO(Message message);
}

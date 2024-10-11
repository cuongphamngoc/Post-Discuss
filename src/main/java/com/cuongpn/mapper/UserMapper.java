package com.cuongpn.mapper;

import com.cuongpn.dto.responeDTO.AuthorDTO;
import com.cuongpn.dto.responeDTO.UserResponseDTO;
import com.cuongpn.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "id", target = "userId")
    UserResponseDTO userToUserResponseDTO (User user);

    AuthorDTO toAuthor(User user);
}

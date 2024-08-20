package com.cuongpn.mapper;

import com.cuongpn.dto.responeDTO.UserResponseDTO;
import com.cuongpn.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "userid", target = "userId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email",target = "email")
    UserResponseDTO userToUserResponseDTO (User user);
}

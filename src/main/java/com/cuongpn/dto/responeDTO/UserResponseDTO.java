package com.cuongpn.dto.responeDTO;

import com.cuongpn.entity.User;
import lombok.Data;

@Data
public class UserResponseDTO {
    private int userId;

    private String fullName;

    private String email;
    private String avatarUrl;
    public UserResponseDTO(User user){

    }

}

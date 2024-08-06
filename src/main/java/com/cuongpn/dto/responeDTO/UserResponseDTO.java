package com.cuongpn.dto.responeDTO;

import com.cuongpn.entity.User;
import lombok.Data;

@Data
public class UserResponseDTO {
    private int userId;

    private String name;

    private String email;
    private String about;
    public UserResponseDTO(User user){

    }

}

package com.cuongpn.service;

import com.cuongpn.dto.requestDTO.RegisterRequest;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.dto.responeDTO.UserResponseDTO;


import java.util.List;


public interface UserService {
    ResponseData<List<UserResponseDTO>> getAllUser();

    ResponseData<UserResponseDTO> getUserById(Integer id) ;


}

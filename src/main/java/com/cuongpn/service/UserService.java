package com.cuongpn.service;

import com.cuongpn.dto.requestDTO.ChangePasswordRequestDTO;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.dto.responeDTO.UserResponseDTO;
import com.cuongpn.entity.User;
import com.cuongpn.security.services.CurrentUser;
import com.cuongpn.security.services.UserPrincipal;


import java.util.List;


public interface UserService {
    ResponseData<List<UserResponseDTO>> getAllUser();

    ResponseData<UserResponseDTO> getUserById(Long id) ;

    boolean isExistMail(String mail);
    User checkAndCreateUser(String email);

    User getUserByMail(String email);


    ResponseData<?> changePassword(@CurrentUser UserPrincipal currentUser, ChangePasswordRequestDTO changePasswordRequestDto);
    ResponseData<?> bookMarkArticle(@CurrentUser UserPrincipal current, Long id);

}

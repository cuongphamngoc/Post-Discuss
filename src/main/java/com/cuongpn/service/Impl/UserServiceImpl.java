package com.cuongpn.service.Impl;

import com.cuongpn.dto.requestDTO.RegisterRequest;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.dto.responeDTO.UserResponseDTO;
import com.cuongpn.entity.User;
import com.cuongpn.mapper.UserMapper;
import com.cuongpn.repository.UserRepo;
import com.cuongpn.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepo userRepo;

    private UserMapper userMapper;

    private PasswordEncoder passwordEncoder;
    @Override
    public ResponseData<List<UserResponseDTO>> getAllUser() {
        List<UserResponseDTO> data =  userRepo.findAll().stream().map(user -> userMapper.userToUserResponseDTO(user)).toList();
        ResponseData<List<UserResponseDTO>> responseData = new ResponseData<>(HttpStatus.OK.value(), "Success",data);
        return responseData;
    }

    @Override
    public ResponseData<UserResponseDTO> getUserById(Integer id)  {
        User user = userRepo.findById(id).orElseThrow(()-> {throw new NoSuchElementException("Id not found "+ id);});
        ResponseData<UserResponseDTO> responseData = new ResponseData<>(HttpStatus.OK.value(), "Success",userMapper.userToUserResponseDTO(user));
        return responseData;
    }


}

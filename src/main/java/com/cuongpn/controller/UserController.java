package com.cuongpn.controller;


import com.cuongpn.dto.requestDTO.ChangePasswordRequestDto;
import com.cuongpn.dto.requestDTO.UserRequestDTO;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.dto.responeDTO.UserResponseDTO;
import com.cuongpn.security.services.CurrentUser;
import com.cuongpn.security.services.UserPrincipal;
import com.cuongpn.service.UserService;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }
    @GetMapping("/")
    public ResponseData<List<UserResponseDTO>> getAllUser(){
        return userService.getAllUser();
    }
    @GetMapping("/{id}")
    public ResponseData<UserResponseDTO> getUserbyId(@NonNull @PathVariable Integer id){
        return userService.getUserById(id);
    }
    @PostMapping("/change-password")
    public ResponseData<?> changePassword(@CurrentUser UserPrincipal current,@RequestBody @Valid ChangePasswordRequestDto changePasswordRequestDto){
        System.out.println(changePasswordRequestDto);
        return userService.changePassword(current, changePasswordRequestDto);
    }


}

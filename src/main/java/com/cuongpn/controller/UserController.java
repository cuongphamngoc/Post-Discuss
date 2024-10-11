package com.cuongpn.controller;


import com.cuongpn.dto.requestDTO.PasswordDTO;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.dto.responeDTO.UserResponseDTO;
import com.cuongpn.security.services.CurrentUser;
import com.cuongpn.security.services.UserPrincipal;
import com.cuongpn.service.UserService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    @GetMapping("/")
    public ResponseData<List<UserResponseDTO>> getAllUser(){
        return new ResponseData<>(HttpStatus.OK.value(), "Success",userService.getAllUser());
    }
    @GetMapping("/{id}")
    public ResponseData<UserResponseDTO> getUserById(@NonNull @PathVariable Long id){
        return new ResponseData<>(HttpStatus.OK.value(), "Success",userService.getUserById(id));
    }
    @PostMapping("/change-password")
    public ResponseData<?> changePassword(@CurrentUser UserPrincipal current,@RequestBody @Valid PasswordDTO passwordDto){

        userService.changePassword(current, passwordDto);
        return new ResponseData<>(HttpStatus.OK.value(), "Change password successful!");

    }
    @PostMapping("/bookmarks/{id}")
    public ResponseData<?> bookMarkArticle(@CurrentUser UserPrincipal current, @PathVariable("id") Long id){
        userService.bookMarkArticle(current,id);
        return new ResponseData<>(HttpStatus.OK.value(), "Article bookmarked");
    }



}

package com.cuongpn.controller;


import com.cuongpn.dto.requestDTO.PasswordDTO;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.dto.responeDTO.UserResponseDTO;
import com.cuongpn.mapper.UserMapper;
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
    @PostMapping("/{userId}/follow")
    public ResponseData<?> followingUser(@CurrentUser UserPrincipal userPrincipal, @PathVariable("userId") Long userId){

        return new ResponseData<>(HttpStatus.OK.value(), "Success",userService.handleFollowingUser(userPrincipal,userId));

    }
    @GetMapping("/info")
    public ResponseData<UserResponseDTO> getUserInfo(@CurrentUser UserPrincipal userPrincipal){
        return new ResponseData<>(HttpStatus.OK.value(), "Get user info successful",userService.getUserInfo(userPrincipal));
    }
    @PostMapping("/bookmark/{postId}")
    public ResponseData<?> bookmarkPost(@CurrentUser UserPrincipal userPrincipal, @PathVariable("postId") Long postId){
        return new ResponseData<>(HttpStatus.OK.value(), "Successful",userService.bookmarkPost(userPrincipal,postId));
    }

    @GetMapping("/is-following/{authorId}")
    public ResponseData<?> checkFollowingStatus(@CurrentUser UserPrincipal userPrincipal, @PathVariable("authorId") Long authorId){
        return new ResponseData<>(HttpStatus.OK.value(), "Successful",userService.checkFollowingStatus(userPrincipal,authorId));
    }
    @GetMapping("/is-bookmarked/{postId}")
    public ResponseData<?> checkBookmarkedStatus(@CurrentUser UserPrincipal userPrincipal, @PathVariable("postId") Long postId){
        return new ResponseData<>(HttpStatus.OK.value(), "Successful",userService.checkBookmarkStatus(userPrincipal,postId));
    }







}

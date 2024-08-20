package com.cuongpn.service.Impl;

import com.cuongpn.dto.requestDTO.ChangePasswordRequestDto;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.dto.responeDTO.UserResponseDTO;
import com.cuongpn.entity.User;
import com.cuongpn.mapper.UserMapper;
import com.cuongpn.repository.UserRepo;
import com.cuongpn.security.services.CurrentUser;
import com.cuongpn.security.services.UserPrincipal;
import com.cuongpn.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    @Override
    public boolean isExistMail(String mail) {
        return userRepo.findByEmail(mail).isPresent();
    }

    @Override
    public User checkAndCreateUser(String email) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if(optionalUser.isPresent()) return null;
        User newUser = new User(email,passwordEncoder.encode("12345"),"ROLE_USER");
        userRepo.save(newUser);

        return newUser;
    }

    @Override
    public User getUserByMail(String email) {
        return userRepo.findByEmail(email).orElseThrow(()-> {throw new NoSuchElementException("Email not found "+ email);});

    }

    @Override
    public ResponseData<?> changePassword(@CurrentUser UserPrincipal currentUser, ChangePasswordRequestDto changePasswordRequestDto) {
        User user = userRepo.findByEmail(currentUser.getEmail()).orElseThrow(()->new IllegalArgumentException("Current user is not authenticated!"));

        if(!passwordEncoder.matches(changePasswordRequestDto.getOldPassword(), user.getPassword())){
            throw new IllegalArgumentException("Old password not match");
        }
        user.setPassword(passwordEncoder.encode(changePasswordRequestDto.getNewPassword()));
        userRepo.save(user);
        return new ResponseData<>(HttpStatus.OK.value(), "Change password successful!");
    }


}

package com.cuongpn.service.Impl;

import com.cuongpn.dto.requestDTO.ChangePasswordRequestDTO;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.dto.responeDTO.UserResponseDTO;
import com.cuongpn.entity.Article;
import com.cuongpn.entity.User;
import com.cuongpn.mapper.UserMapper;
import com.cuongpn.repository.UserRepository;
import com.cuongpn.security.services.CurrentUser;
import com.cuongpn.security.services.UserPrincipal;
import com.cuongpn.service.ArticleService;
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
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final ArticleService articleService;

    private final PasswordEncoder passwordEncoder;
    @Override
    public ResponseData<List<UserResponseDTO>> getAllUser() {
        List<UserResponseDTO> data =  userRepository.findAll().stream().map(user -> userMapper.userToUserResponseDTO(user)).toList();
        ResponseData<List<UserResponseDTO>> responseData = new ResponseData<>(HttpStatus.OK.value(), "Success",data);
        return responseData;
    }

    @Override
    public ResponseData<UserResponseDTO> getUserById(Long id)  {
        User user = userRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Id not found " + id));
        return new ResponseData<>(HttpStatus.OK.value(), "Success",userMapper.userToUserResponseDTO(user));
    }

    @Override
    public boolean isExistMail(String mail) {
        return userRepository.findByEmail(mail).isPresent();
    }

    @Override
    public User checkAndCreateUser(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()) return null;
        User newUser = new User(email,passwordEncoder.encode("12345"),"ROLE_USER");
        userRepository.save(newUser);

        return newUser;
    }

    @Override
    public User getUserByMail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()-> new NoSuchElementException("Email not found " + email));

    }

    @Override
    public ResponseData<?> changePassword(@CurrentUser UserPrincipal currentUser, ChangePasswordRequestDTO changePasswordRequestDto) {
        User user = userRepository.findByEmail(currentUser.getEmail()).orElseThrow(()->new IllegalArgumentException("Current user is not authenticated!"));

        if(!passwordEncoder.matches(changePasswordRequestDto.getOldPassword(), user.getPassword())){
            throw new IllegalArgumentException("Old password not match");
        }
        user.setPassword(passwordEncoder.encode(changePasswordRequestDto.getNewPassword()));
        userRepository.save(user);
        return new ResponseData<>(HttpStatus.OK.value(), "Change password successful!");
    }

    @Override
    public ResponseData<?> bookMarkArticle(UserPrincipal current, Long id) {
        User user = userRepository.findByEmail(current.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Current user is not authenticated!"));

        Article article = articleService.getArticleById(id);


        if (user.getBookmarkedArticles().contains(article)) {
            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), "Article already bookmarked");
        }

        user.getBookmarkedArticles().add(article);
        userRepository.save(user);

        return new ResponseData<>(HttpStatus.OK.value(), "Article bookmarked");
    }



}

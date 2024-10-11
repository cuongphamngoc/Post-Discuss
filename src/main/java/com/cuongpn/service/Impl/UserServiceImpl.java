package com.cuongpn.service.Impl;

import com.cuongpn.dto.requestDTO.PasswordDTO;
import com.cuongpn.dto.responeDTO.UserResponseDTO;
import com.cuongpn.entity.Article;
import com.cuongpn.entity.Role;
import com.cuongpn.entity.User;
import com.cuongpn.exception.AppException;
import com.cuongpn.exception.ErrorCode;
import com.cuongpn.mapper.UserMapper;
import com.cuongpn.repository.UserRepository;
import com.cuongpn.security.services.CurrentUser;
import com.cuongpn.security.services.UserPrincipal;
import com.cuongpn.service.ArticleService;
import com.cuongpn.service.RoleService;
import com.cuongpn.service.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ArticleService articleService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    @Override
    public List<UserResponseDTO> getAllUser() {
        return  userRepository.findAll().stream().map(userMapper::userToUserResponseDTO).toList();
    }

    @Override
    public UserResponseDTO getUserById(Long id)  {
        User user = userRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.userToUserResponseDTO(user);
    }

    @Override
    public User checkAndCreateUser(GoogleIdToken.Payload payload) {
        return userRepository.findByEmail(payload.getEmail()).orElseGet(()->{
              Role roleUser = roleService.findRoleByName("ROLE_USER");
              User user = User.builder()
                      .name((String) payload.get("name"))
                      .isVerification(true)
                      .avatarUrl((String) payload.get("picture"))
                      .roles(Set.of(roleUser))
                      .email(payload.getEmail())
                      .build();
              return  userRepository.save(user);
        });
    }

    @Override
    public boolean isExistMail(String mail) {
        return userRepository.findByEmail(mail).isPresent();
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User getUserByMail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()-> new NoSuchElementException("Email not found " + email));

    }

    @Override
    public void changePassword(@CurrentUser UserPrincipal currentUser, PasswordDTO passwordDto) {
        User user = userRepository.findByEmail(currentUser.getEmail()).orElseThrow(()->new IllegalArgumentException("Current user is not authenticated!"));

        if(!passwordEncoder.matches(passwordDto.getOldPassword(), user.getPassword())){
            throw new AppException(ErrorCode.OLD_PASSWORD_NOT_MATCH);
        }
        user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public void bookMarkArticle(UserPrincipal current, Long id) {
        User user = userRepository.findByEmail(current.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Article article = articleService.getArticleById(id);


    }



}

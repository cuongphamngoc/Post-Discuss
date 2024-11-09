package com.cuongpn.service;

import com.cuongpn.dto.requestDTO.PasswordDTO;
import com.cuongpn.dto.responeDTO.UserResponseDTO;
import com.cuongpn.entity.User;
import com.cuongpn.security.services.CurrentUser;
import com.cuongpn.security.services.UserPrincipal;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;


import java.util.List;


public interface UserService {
    List<UserResponseDTO> getAllUser();
    UserResponseDTO getUserById(Long id) ;
    UserResponseDTO getUserInfo(UserPrincipal userPrincipal);
    User checkAndCreateUser(GoogleIdToken.Payload payload);
    boolean isExistMail(String mail);
    User getUserByMail(String email);
    void changePassword(@CurrentUser UserPrincipal currentUser, PasswordDTO passwordDto);
    boolean handleFollowingUser( @CurrentUser UserPrincipal current, Long userId);

    boolean checkFollowingStatus(UserPrincipal userPrincipal, Long authorId);
    boolean checkBookmarkStatus(UserPrincipal userPrincipal, Long postId);

    boolean bookmarkPost(UserPrincipal userPrincipal, Long postId);


}

package com.cuongpn.service;

import com.cuongpn.dto.requestDTO.*;
import com.cuongpn.dto.responeDTO.TokenResponse;
import com.cuongpn.dto.responeDTO.UserResponseDTO;
import com.cuongpn.entity.User;
import com.cuongpn.exception.UserAlreadyExistException;

import java.io.IOException;

public interface AuthenticationService {

    public TokenResponse login(LoginRequestDTO request) ;

    TokenResponse login(User user);

    UserResponseDTO register(RegisterRequestDTO request) throws UserAlreadyExistException;

    public void forgotPassword(ForgotPasswordDTO request);

    public void verifyAccount(String token );

    void resetPassword(ResetPasswordDTO resetPasswordDto);

    TokenResponse loginWithGoogle(RefreshTokenDTO request) throws IOException;
    boolean emailExists(String mail);

    TokenResponse getNewToken(RefreshTokenDTO refreshToken);
}

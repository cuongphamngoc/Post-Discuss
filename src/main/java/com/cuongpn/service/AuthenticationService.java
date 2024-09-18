package com.cuongpn.service;

import com.cuongpn.dto.requestDTO.*;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.dto.responeDTO.TokenResponse;
import com.cuongpn.dto.responeDTO.UserResponseDTO;
import com.cuongpn.entity.User;
import com.cuongpn.exception.UserAlreadyExistException;

import java.io.IOException;

public interface AuthenticationService {

    public ResponseData<TokenResponse> login(LoginRequestDTO request) ;

    ResponseData<TokenResponse> login(User user);

    ResponseData<UserResponseDTO> register(RegisterRequestDTO request) throws UserAlreadyExistException;

    public ResponseData<?> forgot(ForgotPasswordRequest request);

    public ResponseData<?> verifyAccount(String token );

    ResponseData<?> reset( ResetPasswordRequestDTO resetPasswordRequestDto);

    ResponseData<TokenResponse> loginWithGoogle(RefreshTokenRequestDTO request) throws IOException;
    boolean emailExists(String mail);

    ResponseData<?> getNewToken(RefreshTokenRequestDTO refreshToken);
}

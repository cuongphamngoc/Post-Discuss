package com.cuongpn.service;

import com.cuongpn.dto.requestDTO.*;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.dto.responeDTO.TokenResponse;
import com.cuongpn.dto.responeDTO.UserResponseDTO;
import com.cuongpn.exception.UserAlreadyExistException;

public interface AuthenticationService {

    public ResponseData<TokenResponse> login(LoginRequest request) ;

    ResponseData<UserResponseDTO> register(RegisterRequest request) throws UserAlreadyExistException;

    public ResponseData<Void> forgot(ForgotPasswordRequest request);

    public ResponseData<Void> verifyAccount(VerificationRequest verificationRequest);

    ResponseData<Void> reset(String token, ResetPasswordRequestDto resetPasswordRequestDto);
}

package com.cuongpn.controller;

import com.cuongpn.dto.requestDTO.*;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.dto.responeDTO.TokenResponse;
import com.cuongpn.dto.responeDTO.UserResponseDTO;

import com.cuongpn.service.AuthenticationService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private AuthenticationService authenticationService;
    @PostMapping("/login")
    public ResponseData<TokenResponse> login(@Valid @RequestBody LoginRequestDTO request){
        return authenticationService.login(request);

    }
    @GetMapping("/email-exists/{email}")
    public boolean checkEmailExist(@PathVariable("email") String email){
        return authenticationService.emailExists(email);
    }
    @PostMapping("/login/google")
    public ResponseData<TokenResponse> loginWithGoogle(@Valid @RequestBody RefreshTokenRequestDTO request) throws IOException {
        return authenticationService.loginWithGoogle(request);
    }
    @PostMapping("/register")
    public ResponseData<UserResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request)  {
        return authenticationService.register(request);

    }
    @PostMapping("/forgot-password")
    public ResponseData<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request){
        return authenticationService.forgot(request);
    }
    @GetMapping("/verify-account/{token}")
    public ResponseData<?> accountVerification(@PathVariable("token") String token){
        return authenticationService.verifyAccount(token);
    }
    @PostMapping("/reset-password")
    public ResponseData<?> resetPassword( @RequestBody ResetPasswordRequestDTO resetPasswordRequestDto){
        return authenticationService.reset(resetPasswordRequestDto);
    }
    @PostMapping("/refresh-token")
    public ResponseData<?> getNewToken(@Valid @RequestBody RefreshTokenRequestDTO refreshToken){
        return authenticationService.getNewToken(refreshToken);
    }
}

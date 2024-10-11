package com.cuongpn.controller;

import com.cuongpn.dto.requestDTO.*;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.dto.responeDTO.TokenResponse;
import com.cuongpn.dto.responeDTO.UserResponseDTO;

import com.cuongpn.service.AuthenticationService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private AuthenticationService authenticationService;
    @PostMapping("/login")
    public ResponseData<TokenResponse> login(@Valid @RequestBody LoginRequestDTO request){
        return new ResponseData<>(HttpStatus.OK.value(),
                "Login Success!", authenticationService.login(request));

    }
    @GetMapping("/email-exists/{email}")
    public boolean checkEmailExist(@PathVariable("email") String email){
        return authenticationService.emailExists(email);
    }
    @PostMapping("/login/google")
    public ResponseData<TokenResponse> loginWithGoogle(@Valid @RequestBody RefreshTokenDTO request) throws IOException {
        return new ResponseData<>(HttpStatus.OK.value(),"Login Success!",
            authenticationService.loginWithGoogle(request));
    }
    @PostMapping("/register")
    public ResponseData<UserResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request)  {
        return new ResponseData<>(HttpStatus.OK.value(),"Register Successful!",
                authenticationService.register(request));

    }
    @PostMapping("/forgot-password")
    public ResponseData<?> forgotPassword(@Valid @RequestBody ForgotPasswordDTO request){
        authenticationService.forgotPassword(request);
        return new ResponseData<>(HttpStatus.OK.value(), "Mail sent successful");

    }
    @GetMapping("/verify-account/{token}")
    public ResponseData<?> accountVerification(@PathVariable("token") String token){
        authenticationService.verifyAccount(token);
        return new ResponseData<>(HttpStatus.OK.value(), "Account Verification successful");
    }
    @PostMapping("/reset-password")
    public ResponseData<?> resetPassword( @RequestBody ResetPasswordDTO resetPasswordDto){
        authenticationService.resetPassword(resetPasswordDto);
        return new ResponseData<>(HttpStatus.OK.value(), "Password change successful");
    }
    @PostMapping("/refresh-token")
    public ResponseData<TokenResponse> getNewToken(@Valid @RequestBody RefreshTokenDTO refreshToken){
        return new ResponseData<>(HttpStatus.OK.value(),
                "Refreshing token successful", authenticationService.getNewToken(refreshToken));
    }
}

package com.cuongpn.controller;

import com.cuongpn.dto.requestDTO.*;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.dto.responeDTO.TokenResponse;
import com.cuongpn.dto.responeDTO.UserResponseDTO;
import com.cuongpn.security.Jwt.JwtProvider;
import com.cuongpn.service.AuthenticationService;
import com.cuongpn.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private JwtProvider jwtProvider;
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private BCryptPasswordEncoder passwordEncoder;
    private AuthenticationService authenticationService;
    @PostMapping("/login")
    public ResponseData<TokenResponse> login(@Valid @RequestBody LoginRequest request){
        return authenticationService.login(request);

    }
    @PostMapping("/register")
    public ResponseData<UserResponseDTO> register(@Valid @RequestBody RegisterRequest request)  {
        return authenticationService.register(request);

    }
    @PostMapping("/forgot-password")
    public ResponseData<Void> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request){
        return authenticationService.forgot(request);
    }
    @PostMapping("/verify-account")
    public ResponseData<Void> accountVerification(@Valid @RequestBody VerificationRequest verificationRequest){
        return new ResponseData<>(HttpStatus.OK.value(), "");
    }
    @PostMapping("/reset-password")
    public ResponseData<Void> resetPassword(@RequestParam String token, @RequestBody ResetPasswordRequestDto resetPasswordRequestDto){
        return authenticationService.reset(token,resetPasswordRequestDto);
    }
}

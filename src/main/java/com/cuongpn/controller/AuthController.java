package com.cuongpn.controller;

import com.cuongpn.dto.requestDTO.*;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.dto.responeDTO.TokenResponse;
import com.cuongpn.dto.responeDTO.UserResponseDTO;

import com.cuongpn.service.AuthenticationService;

import com.cuongpn.service.TokenBlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final TokenBlacklistService tokenBlacklistService;

    @PostMapping("/login")
    public ResponseData<?> login(@Valid @RequestBody LoginRequestDTO request, HttpServletResponse response) {
        TokenResponse tokenResponse = authenticationService.login(request);
        addCookie(response,"accessToken",tokenResponse.getAccessToken(),60*60);
        addCookie(response,"refreshToken",tokenResponse.getRefreshToken(),14*24*60*60);
        return new ResponseData<>(HttpStatus.OK.value(),
                "Login Success!");

    }

    @GetMapping("/email-exists/{email}")
    public boolean checkEmailExist(@PathVariable("email") String email) {
        return authenticationService.emailExists(email);
    }

    @PostMapping("/oauth2-authentication/{provider}")
    public ResponseData<TokenResponse> loginWithOAuth2(@RequestParam("code") String code,
                                                       @PathVariable("provider") String provider) throws IOException {
        TokenResponse tokenResponse = authenticationService.OAuth2Authentication(code, provider);

        return new ResponseData<>(HttpStatus.OK.value(), "Login Success!");
    }

    @PostMapping("/register")
    public ResponseData<UserResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        return new ResponseData<>(HttpStatus.OK.value(), "Register Successful!",
                authenticationService.register(request));

    }

    @PostMapping("/forgot-password")
    public ResponseData<?> forgotPassword(@Valid @RequestBody ForgotPasswordDTO request) {
        authenticationService.forgotPassword(request);
        return new ResponseData<>(HttpStatus.OK.value(), "Mail sent successful");

    }

    @GetMapping("/verify-account/{token}")
    public ResponseData<?> accountVerification(@PathVariable("token") String token) {
        authenticationService.verifyAccount(token);
        return new ResponseData<>(HttpStatus.OK.value(), "Account Verification successful");
    }

    @PostMapping("/reset-password")
    public ResponseData<?> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDto) {
        authenticationService.resetPassword(resetPasswordDto);
        return new ResponseData<>(HttpStatus.OK.value(), "Password change successful");
    }

    @PostMapping("/refresh-token")
    public ResponseData<?> getNewToken(@CookieValue(value = "refreshToken") String refreshToken,
                                           HttpServletRequest request, HttpServletResponse response) {
        TokenResponse tokenResponse =  authenticationService.getNewToken(refreshToken);
        addCookie(response,"accessToken",tokenResponse.getAccessToken(),60*60);
        addCookie(response,"refreshToken",tokenResponse.getRefreshToken(),14*24*60*60);
        return new ResponseData<>(HttpStatus.OK.value(),
                "Refreshing token successful");
    }

    @PostMapping("/logout")
    public ResponseData<?> handleLogOut(@CookieValue(value = "accessToken") String accessToken,
                                        @CookieValue(value = "refreshToken") String refreshToken,
                                        HttpServletResponse response){

        tokenBlacklistService.addTokenToBlacklist(accessToken);
        tokenBlacklistService.addTokenToBlacklist(refreshToken);

        addCookie(response,"accessToken",null,0);
        addCookie(response,"refreshToken",null,0);
        return new ResponseData<>(HttpStatus.OK.value(),
                "Logout successful");

    }

    private void addCookie(HttpServletResponse response,String name, String value, int maxAge) {
        ResponseCookie cookie = ResponseCookie.from(name,value)
                .httpOnly(true)
                .sameSite("Strict")
                .path("/")
                .httpOnly(true)
                .maxAge(maxAge)
                .build();
        response.addHeader("Set-Cookie",cookie.toString());

    }

}
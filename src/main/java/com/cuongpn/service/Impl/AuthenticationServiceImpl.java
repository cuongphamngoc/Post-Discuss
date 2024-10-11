package com.cuongpn.service.Impl;

import com.cuongpn.dto.requestDTO.*;
import com.cuongpn.dto.responeDTO.TokenResponse;
import com.cuongpn.dto.responeDTO.UserResponseDTO;
import com.cuongpn.entity.Role;
import com.cuongpn.entity.User;
import com.cuongpn.exception.AppException;
import com.cuongpn.exception.ErrorCode;
import com.cuongpn.exception.UserAlreadyExistException;
import com.cuongpn.mapper.UserMapper;
import com.cuongpn.repository.UserRepository;
import com.cuongpn.security.Jwt.JwtProvider;
import com.cuongpn.security.services.UserPrincipal;
import com.cuongpn.service.*;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import io.jsonwebtoken.Claims;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;
    @Value("${secret.password}")
    private String secretPassword;
    @Value("${client.domain.url}")
    private String clientUrl;
    @Value("${user.default.avatar.url}")
    private String avatarUrl;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final OtpService otpService;
    private final RoleService roleService;



    @Override
    public TokenResponse login(LoginRequestDTO request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println(authentication.getPrincipal());
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return new TokenResponse(jwtProvider.generateAccessToken(userPrincipal),
                jwtProvider.generateRefreshToken(userPrincipal));

    }

    @Override
    public TokenResponse login(User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        passwordEncoder.encode(secretPassword),
                        user.getRoles().stream().map(role-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
                ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return new TokenResponse(jwtProvider.generateAccessToken(userPrincipal),
                jwtProvider.generateRefreshToken(userPrincipal));
    }

    @Override
    public UserResponseDTO register(RegisterRequestDTO request) throws UserAlreadyExistException{
        String email = request.getEmail();
        Role roleUser = roleService.findRoleByName("ROLE_USER");
        if (userService.isExistMail(email)) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTED);
        }
        String verifyToken = generateToken();
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getFullName())
                .roles(Set.of(roleUser))
                .avatarUrl(avatarUrl)
                .build();

        user.setVerificationToken(verifyToken);
        User res = userRepository.save(user);
        String verificationUrl = clientUrl + "/verify-account?token=" + verifyToken;
        emailService.sendVerificationEmail(user,verificationUrl);

        return userMapper.userToUserResponseDTO(res);
    }

    @Override
    public void forgotPassword(ForgotPasswordDTO request) {
        String email = request.getEmail();
        log.info("Searching for email: " + email);
        User user = userRepository.findByEmail(email).orElseThrow(()-> new AppException(ErrorCode.EMAIL_NOT_EXISTED));
        String verifyToken = generateToken();
        otpService.saveMail(verifyToken,email);
        String resetPasswordUrl = clientUrl+"/reset-password?token=" + verifyToken;
        emailService.sendResetPasswordEmail(user,resetPasswordUrl);
    }

    @Override
    public void verifyAccount(String token) {
        User user = userRepository.findByVerificationToken(token).orElseThrow(
                ()-> new AppException(ErrorCode.VERIFICATION_TOKEN_INVALID));
        user.setVerification(true);
        user.setVerificationToken(null);
        userRepository.save(user);
    }

    @Override
    public void resetPassword(ResetPasswordDTO resetPasswordDto) {
        String email = otpService.getMail(resetPasswordDto.getToken());
        User user = userRepository.findByEmail(email).orElseThrow(()-> new AppException(ErrorCode.EMAIL_NOT_EXISTED));
        user.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public TokenResponse loginWithGoogle(RefreshTokenDTO request) throws IOException {
        final NetHttpTransport transport = new NetHttpTransport();
        final JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
        GoogleIdTokenVerifier.Builder verifier = new GoogleIdTokenVerifier.Builder(transport,jacksonFactory)
                .setAudience(Collections.singletonList(googleClientId));
        final GoogleIdToken googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(),request.getToken());
        final GoogleIdToken.Payload payload = googleIdToken.getPayload();
        User user = userService.checkAndCreateUser(payload);
        return login(user);
    }



    public boolean emailExists(String mail) {
        return userRepository.findByEmail(mail).isPresent();
    }

    @Override
    public TokenResponse getNewToken(RefreshTokenDTO refreshToken) {
        jwtProvider.validateToken(refreshToken.getToken());
        Claims claims = jwtProvider.getClaimsFromToken(refreshToken.getToken());
        String newAccessToken = jwtProvider.generateAccessTokenFromClaims(claims);
        String newRefreshToken = jwtProvider.generateRefreshTokenFromClaims(claims);
        return new TokenResponse(newAccessToken,newRefreshToken);

    }

    private String generateToken(){
        return new DecimalFormat("000000000").format(new Random().nextInt(999999));
    }
}

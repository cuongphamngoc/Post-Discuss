package com.cuongpn.service.Impl;

import com.cuongpn.dto.requestDTO.*;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.dto.responeDTO.TokenResponse;
import com.cuongpn.dto.responeDTO.UserResponseDTO;
import com.cuongpn.entity.EmailDetails;
import com.cuongpn.entity.User;
import com.cuongpn.exception.UserAlreadyExistException;
import com.cuongpn.mapper.UserMapper;
import com.cuongpn.repository.UserRepository;
import com.cuongpn.security.Jwt.JwtProvider;
import com.cuongpn.security.services.UserPrincipal;
import com.cuongpn.service.AuthenticationService;
import com.cuongpn.service.EmailSenderService;
import com.cuongpn.service.OtpService;
import com.cuongpn.service.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import io.jsonwebtoken.Claims;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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

@Data
@Service

@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;
    @Value("${secret.password}")
    private String secretPassword;
    private final AuthenticationManager authenticationManager;
    private final EmailSenderService emailSenderService;
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final OtpService otpService;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, EmailSenderService emailSenderService, UserService userService, JwtProvider jwtProvider, UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, OtpService otpService) {
        this.authenticationManager = authenticationManager;
        this.emailSenderService = emailSenderService;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.otpService = otpService;
    }

    @Override
    public ResponseData<TokenResponse> login(LoginRequestDTO request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println(authentication.getPrincipal());
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        TokenResponse token = new TokenResponse(jwtProvider.generateAccessToken(userPrincipal),
                jwtProvider.generateRefreshToken(userPrincipal));
        
        return new ResponseData<>(HttpStatus.OK.value(),"Login Success!",token);

    }

    @Override
    public ResponseData<TokenResponse> login(User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        passwordEncoder.encode(secretPassword),
                        Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
                ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        TokenResponse token = new TokenResponse(jwtProvider.generateAccessToken(userPrincipal),
                jwtProvider.generateRefreshToken(userPrincipal));
        return new ResponseData<>(HttpStatus.OK.value(),"Login Success!",token);
    }

    @Override
    public ResponseData<UserResponseDTO> register(RegisterRequestDTO request) throws UserAlreadyExistException{
        String email = request.getEmail();

        if (userService.isExistMail(email)) {
            throw new IllegalArgumentException("Email already exists: " + email);
        }
        String verifyToken = generateToken();
        User user = new User(request.getEmail(),passwordEncoder.encode(request.getPassword()),request.getFullName(),"ROLE_USER");
        user.setVerificationToken(verifyToken);
        User res = userRepository.save(user);
        String verificationUrl = "http://localhost:4200/verify-account?token=" + verifyToken;
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setSubject("Verify Your Account");
        emailDetails.setRecipient(user.getEmail());
        emailDetails.setMsgBody("<!DOCTYPE html>"
                + "<html>"
                + "<head><meta charset=\"UTF-8\"><title>Verify Account</title></head>"
                + "<body>"
                + "<p>Hi " + user.getName() + ",</p>"
                + "<p>Thank you for signing up with us.</p>"
                + "<p>To complete your registration and verify your account, please click the link below:</p>"
                + "<p><a href=\"" + verificationUrl + "\">Verify Account</a></p>"
                + "<p>If you did not sign up for this account, you can ignore this email.</p>"
                + "<p>Best regards,<br>The Support Team</p>"
                + "</body>"
                + "</html>");
        emailSenderService.sendMailWithAttachment(emailDetails);
        return new ResponseData<>(HttpStatus.CREATED.value(),"Account created, please check mail to verify your account!",userMapper.userToUserResponseDTO(res));
    }

    @Override
    public ResponseData<?> forgot(ForgotPasswordRequest request) {
        String email = request.getEmail();
        log.info("Searching for email: " + email);
        User user = userRepository.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("Email not found: " + email));

        String verifyToken = generateToken();
        otpService.saveMail(verifyToken,email);
        String linkConfirm = "http://localhost:4200/reset-password?token=" + verifyToken;
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(user.getEmail());
        emailDetails.setSubject("Password Reset Request");
        emailDetails.setMsgBody("<!DOCTYPE html>"
                + "<html>"
                + "<head><meta charset=\"UTF-8\"><title>Password Reset</title></head>"
                + "<body>"
                + "<p>Hi " + user.getName() + ",</p>"
                + "<p>We received a request to reset the password for your account.</p>"
                + "<p>To reset your password, please click the link below:</p>"
                + "<p><a href=\"" + linkConfirm + "\">Reset Password</a></p>"
                + "<p>This link will expire in " + 15 + " minutes.</p>"
                + "<p>If you did not request a password reset, you can ignore this email.</p>"
                + "<p>Best regards,<br>The Support Team</p>"
                + "</body>"
                + "</html>");
        emailSenderService.sendMailWithAttachment(emailDetails);
        return new ResponseData<>(HttpStatus.OK.value(), "Mail sent successful");
    }

    @Override
    public ResponseData<?> verifyAccount(String token) {
        User user = userRepository.findByVerificationToken(token).orElseThrow(
                ()-> new IllegalArgumentException("Token invalid ")
        );
        user.setVerification(true);
        user.setVerificationToken(null);
        userRepository.save(user);
        return new ResponseData<>(HttpStatus.OK.value(), "Account Verification successful");
    }

    @Override
    public ResponseData<?> reset( ResetPasswordRequestDTO resetPasswordRequestDto) {
        String email = otpService.getMail(resetPasswordRequestDto.getToken());
        System.out.println(resetPasswordRequestDto);
        User user = userRepository.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("Email not found " +email));
        user.setPassword(passwordEncoder.encode(resetPasswordRequestDto.getNewPassword()));
        userRepository.save(user);
        return new ResponseData<>(HttpStatus.OK.value(), "Password change success");
    }

    @Override
    public ResponseData<TokenResponse> loginWithGoogle(RefreshTokenRequestDTO request) throws IOException {
        final NetHttpTransport transport = new NetHttpTransport();
        final JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
        GoogleIdTokenVerifier.Builder verifier = new GoogleIdTokenVerifier.Builder(transport,jacksonFactory)
                .setAudience(Collections.singletonList(googleClientId));
        final GoogleIdToken googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(),request.getToken());
        final GoogleIdToken.Payload payload = googleIdToken.getPayload();
        User user = userService.checkAndCreateUser(payload.getEmail());
        return login(user);


    }



    public boolean emailExists(String mail) {
        return userRepository.findByEmail(mail).isPresent();
    }

    @Override
    public ResponseData<?> getNewToken(RefreshTokenRequestDTO refreshToken) {
        if(jwtProvider.validateToken(refreshToken.getToken())){
            Claims claims = jwtProvider.getClaimsFromToken(refreshToken.getToken());
            String newAccessToken = jwtProvider.generateAccessTokenFromClaims(claims);
            String newRefreshToken = jwtProvider.generateRefreshTokenFromClaims(claims);
            return new ResponseData<TokenResponse>(HttpStatus.OK.value(), "Get token success",new TokenResponse(newAccessToken,newRefreshToken));
        }
        else{
            throw  new IllegalArgumentException("Token invalid");
        }

    }

    private String generateToken(){
        return new DecimalFormat("000000000").format(new Random().nextInt(999999));
    }
}

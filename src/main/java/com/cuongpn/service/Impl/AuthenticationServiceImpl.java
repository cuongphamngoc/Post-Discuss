package com.cuongpn.service.Impl;

import com.cuongpn.dto.requestDTO.*;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.dto.responeDTO.TokenResponse;
import com.cuongpn.dto.responeDTO.UserResponseDTO;
import com.cuongpn.entity.EmailDetails;
import com.cuongpn.entity.User;
import com.cuongpn.exception.UserAlreadyExistException;
import com.cuongpn.mapper.UserMapper;
import com.cuongpn.repository.UserRepo;
import com.cuongpn.security.Jwt.JwtProvider;
import com.cuongpn.service.AuthenticationService;
import com.cuongpn.service.EmailSenderService;
import com.cuongpn.service.OtpService;
import com.cuongpn.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Random;

@Data
@Service
@AllArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private AuthenticationManager authenticationManager;
    private EmailSenderService emailSenderService;
    private UserService userService;
    private JwtProvider jwtProvider;
    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;
    private UserMapper userMapper;
    private OtpService otpService;
    @Override
    public ResponseData<TokenResponse> login(LoginRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        TokenResponse token = new TokenResponse(jwtProvider.generateAcessToken(authentication.getName()));

        return new ResponseData<>(HttpStatus.OK.value(),"Login Success!",token);

    }

    @Override
    public ResponseData<UserResponseDTO> register(RegisterRequest request) throws UserAlreadyExistException{
        String username = request.getUsername();

        if (usernameExists(username)) {
            throw new UserAlreadyExistException("Username already exists: " + username);
        }
        User user = new User(request.getUsername(),passwordEncoder.encode(request.getPassword()),request.getEmail(),"ROLE_USER");
        User res = userRepo.save(user);
        return new ResponseData<>(HttpStatus.CREATED.value(),"Register success!",userMapper.userToUserResponseDTO(res));
    }

    @Override
    public ResponseData<Void> forgot(ForgotPasswordRequest request) {
        String email = request.getEmail();
        log.info("Searching for email: " + email);
        User user = userRepo.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("Email not found: " + email));

        String verifyToken = generateToken();
        otpService.saveMail(verifyToken,email);
        String linkConfirm = "http://localhost:8080/reset-password?token=" + verifyToken;
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(user.getEmail());
        emailDetails.setSubject("Verify your account");
        emailDetails.setMsgBody("Please click the link to verify your account. " + linkConfirm);
        emailSenderService.sendSimpleMail(emailDetails);
        return new ResponseData<>(HttpStatus.OK.value(), "Token has send to email, please check");
    }

    @Override
    public ResponseData<Void> verifyAccount(VerificationRequest verificationRequest) {
        return null;
    }

    @Override
    public ResponseData<Void> reset(String token, ResetPasswordRequestDto resetPasswordRequestDto) {
        String email = otpService.getMail(token);
        User user = userRepo.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("Email not found " +email));
        user.setPassword(passwordEncoder.encode(resetPasswordRequestDto.getNewPassword()));
        userRepo.save(user);
        return new ResponseData<>(HttpStatus.OK.value(), "Password change success");
    }

    private boolean usernameExists(String username) {
        return userRepo.findByUsername(username).isPresent();
    }

    private String generateToken(){
        return new DecimalFormat("000000").format(new Random().nextInt(999999));
    }
}

package com.cuongpn.service.Impl;

import com.cuongpn.entity.EmailDetail;
import com.cuongpn.entity.User;
import com.cuongpn.service.EmailSenderService;
import com.cuongpn.service.EmailService;
import com.cuongpn.util.EmailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final EmailSenderService emailSenderService;
    @Override
    public void sendVerificationEmail(User user, String verificationUrl) {
        String subject = "Verify Your Account";
        String messageBody = "Thank you for signing up with us. To complete your registration and verify your account, please click the link below:";
        String emailContent = EmailUtil.buildEmailContent(user.getName(), messageBody, verificationUrl, "Verify Account");
        String recipient =  user.getEmail();
        EmailDetail emailDetail = EmailDetail.builder()
                .recipient(recipient)
                .subject(subject)
                .msgBody(emailContent)
                .build();

        emailSenderService.sendMailWithAttachment(emailDetail);
    }

    @Override
    public void sendResetPasswordEmail(User user, String resetPasswordUrl) {
        String subject = "Reset Your Password";
        String messageBody = "We received a request to reset your password. To reset your password, please click the link below:";
        String emailContent = EmailUtil.buildEmailContent(user.getName(), messageBody, resetPasswordUrl, "Reset Password");
        String recipient =  user.getEmail();
        EmailDetail emailDetail = EmailDetail.builder()
                .recipient(recipient)
                .subject(subject)
                .msgBody(emailContent)
                .build();
        emailSenderService.sendMailWithAttachment(emailDetail);
    }
}

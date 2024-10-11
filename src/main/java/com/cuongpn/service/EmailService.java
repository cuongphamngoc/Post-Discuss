package com.cuongpn.service;

import com.cuongpn.entity.User;

public interface EmailService {
    void sendVerificationEmail(User user, String verificationUrl);
    void sendResetPasswordEmail(User user, String verificationUrl);

}

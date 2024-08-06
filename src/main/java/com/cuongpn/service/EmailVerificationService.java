package com.cuongpn.service;

public interface EmailVerificationService {
    public boolean verifyEmail(String email, String token);
}

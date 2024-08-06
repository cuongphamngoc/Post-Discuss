package com.cuongpn.service;

public interface OtpService {
    public void saveMail(String mail, String otp);
    public String getMail(String otp);
    public void removeMail(String otp);
}

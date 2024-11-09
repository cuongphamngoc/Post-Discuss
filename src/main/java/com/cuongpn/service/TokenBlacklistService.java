package com.cuongpn.service;

public interface TokenBlacklistService {
    void addTokenToBlacklist(String token);
    boolean isTokenBlacklisted(String token);
}

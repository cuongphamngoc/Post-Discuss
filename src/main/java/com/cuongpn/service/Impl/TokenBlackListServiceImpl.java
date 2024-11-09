package com.cuongpn.service.Impl;

import com.cuongpn.security.Jwt.JwtProvider;
import com.cuongpn.service.TokenBlacklistService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenBlackListServiceImpl implements TokenBlacklistService {

    private final RedisTemplate<String,String> redisTemplate;
    private final JwtProvider jwtProvider;
    @Override
    public void addTokenToBlacklist(String token){
        if(token == null) return;
        if(jwtProvider.validateToken(token)) {
            Claims claims = jwtProvider.getClaimsFromToken(token);
            Date exp = claims.getExpiration();
            long remainTime = exp.getTime() - System.currentTimeMillis();
            if (remainTime < 0) {
                log.info("Token đã hết hạn và không cần thêm vào blacklist");
                return;
            }
            addTokenToBlacklist(token, remainTime);
        }
    }

    public void addTokenToBlacklist(String token, long expirationTime) {

        redisTemplate.opsForValue().set(token, "blacklisted", Duration.ofMillis(expirationTime));

    }

    @Override
    public boolean isTokenBlacklisted(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(token));
    }


}

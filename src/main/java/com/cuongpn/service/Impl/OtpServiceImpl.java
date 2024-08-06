package com.cuongpn.service.Impl;

import com.cuongpn.service.OtpService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class OtpServiceImpl implements OtpService {
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public void saveMail(String otp,String email) {
        redisTemplate.opsForValue().set(otp, email, 15, TimeUnit.MINUTES);
    }

    @Override
    public String getMail(String otp) {
        return redisTemplate.opsForValue().get(otp);
    }

    @Override
    public void removeMail(String otp) {
        redisTemplate.delete(otp);
    }
}

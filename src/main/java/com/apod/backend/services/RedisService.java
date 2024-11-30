package com.apod.backend.services;

import com.apod.backend.entities.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private TokenService tokenService;

    public void set(String key, String value, Integer timeoutInSeconds) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, timeoutInSeconds, TimeUnit.SECONDS);
    }

    public void setTokenByEmail(String key) {
        String tokenValue = tokenService.generateToken();

        redisTemplate.opsForValue().set(key, tokenValue);
        redisTemplate.expire(key, 60, TimeUnit.SECONDS);
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
package com.apod.backend.entities;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenService {
    public String generateToken() {
        int token = Math.abs(UUID.randomUUID().toString().hashCode() % 10000);
        return String.format("%04d", token);
    }
}

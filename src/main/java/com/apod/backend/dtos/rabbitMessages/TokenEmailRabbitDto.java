package com.apod.backend.dtos.rabbitMessages;

public record TokenEmailRabbitDto(String to, String username, String token) {
}

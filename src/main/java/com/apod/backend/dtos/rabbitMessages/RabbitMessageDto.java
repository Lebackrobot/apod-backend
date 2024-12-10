package com.apod.backend.dtos.rabbitMessages;

public record RabbitMessageDto(String to, String username, String token, String type) {
}

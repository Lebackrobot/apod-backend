package com.apod.backend.dtos.rabbitMessages;

public record SubscriptionConfirmationMessageDto(String email, String name, String token) {
}

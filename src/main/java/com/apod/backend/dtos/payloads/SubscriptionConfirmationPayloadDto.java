package com.apod.backend.dtos.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SubscriptionConfirmationPayloadDto(
        @NotBlank String token,
        @Email String email,
        @NotBlank String name
) {
}
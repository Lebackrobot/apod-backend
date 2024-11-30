package com.apod.backend.dtos.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SubscriptionValidationPayloadDto(
        @NotBlank String token,
        @Email String email,
        @NotBlank String name
) {
}

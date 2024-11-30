package com.apod.backend.dtos.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SubscriptionPayloadDto(@NotBlank String name, @NotBlank @Email String email) {
}

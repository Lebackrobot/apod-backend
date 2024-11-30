package com.apod.backend.Dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SubscriptionDto(@NotBlank String name, @NotBlank @Email String email) {
}

package com.apod.backend.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PayloadSubscriptionDto(@NotBlank String name, @NotBlank @Email String email) {
}

package com.example.pizza.requestDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthRequestDto(
        @NotBlank(message = "Email is required")
        @Email(message = "Email should not be null")
        String email,

        @NotBlank(message = "Password should not be null")
        String password
) {}

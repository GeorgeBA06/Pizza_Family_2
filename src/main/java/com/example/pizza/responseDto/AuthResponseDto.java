package com.example.pizza.responseDto;

import com.example.pizza.entity.Role;
import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthResponseDto(
    String token,

    @JsonProperty("refresh_token")
    String refreshToken,

    String name,

    String email,

    Role role
)
{}

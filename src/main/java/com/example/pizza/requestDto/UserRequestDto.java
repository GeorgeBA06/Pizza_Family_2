package com.example.pizza.requestDto;

import com.example.pizza.entity.Cart;
import com.example.pizza.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


public record UserRequestDto(

        @NotEmpty(message = "Name should not be empty")
        @Size(min = 3, max = 12, message = "Size of the name should be between 3 and 12")
        String name,

        @Email(message = "Email should not be empty")
        @NotEmpty
        String email,

        @NotEmpty(message = "Password should not be empty")
        String password,

        Role role

) {}

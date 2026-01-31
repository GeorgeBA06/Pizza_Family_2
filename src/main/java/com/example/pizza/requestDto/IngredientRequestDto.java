package com.example.pizza.requestDto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record IngredientRequestDto(

        @NotBlank(message = "Ingredient name is required")
        @Size(min = 2, max = 20, message = "Name must be between 2 and 20 characters")
        String name,

        @NotBlank(message = "Price is required")
        @DecimalMin(value = "0.01", message = "Price must be higher than 0")
        BigDecimal price
)
{}

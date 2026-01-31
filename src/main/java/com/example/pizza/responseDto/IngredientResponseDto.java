package com.example.pizza.responseDto;

import java.math.BigDecimal;

public record IngredientResponseDto(
        Long id,
        String name,
        BigDecimal ingredientPrice
)

{

}

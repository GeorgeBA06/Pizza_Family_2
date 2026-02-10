package com.example.pizza.responseDto;

public record CategoryDto(
        String id,
        String displayName,
        String description,
        int displayOrder,
        boolean available

) {
}

package com.example.pizza.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(name = "ingredient_name", nullable = false, unique = true)
    private String name;

    @Column(name = "ingredient_price")
    private BigDecimal ingredientPrice;
}

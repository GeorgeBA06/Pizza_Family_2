package com.example.pizza.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class ExtraIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "extraingredient_price", nullable = false)
    private BigDecimal extraPrice;

    @Column(name = "max_extra_quantity")
    private Integer maxQuantity = 3;

}

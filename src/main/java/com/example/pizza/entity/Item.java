package com.example.pizza.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name", nullable = false)
    private String name;

    @Column(name = "product_price", nullable = false)
    private BigDecimal productPrice;

    @Column(unique = true, nullable = true)
    private String imageUrl;

    @Column(name = "is_active")
    private boolean active;

    @Column(name = "weight/volume", nullable = false)
    private Integer weight;

    @Column(name = "item_unit")
    private String unit;

    @Column(name = "description",
            nullable = true)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    private Category category;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "item_ingredients",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id",
            nullable = true)
    )
    private List<Ingredient> ingredientList;

    @Column(name = "extraingredients")
    private List<ExtraIngredient> extraIngredientsList;

}

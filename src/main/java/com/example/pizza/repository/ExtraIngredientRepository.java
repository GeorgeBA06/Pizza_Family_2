package com.example.pizza.repository;

import com.example.pizza.entity.ExtraIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExtraIngredientRepository extends JpaRepository<ExtraIngredient,Long> {
}

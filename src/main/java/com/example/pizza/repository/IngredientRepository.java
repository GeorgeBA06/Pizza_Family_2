package com.example.pizza.repository;

import com.example.pizza.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient,Long> {
    Optional<Ingredient> findByName(String name);
    boolean existsByName(String name);
}

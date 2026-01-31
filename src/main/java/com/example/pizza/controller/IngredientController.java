package com.example.pizza.controller;

import com.example.pizza.entity.Ingredient;
import com.example.pizza.requestDto.IngredientRequestDto;
import com.example.pizza.responseDto.IngredientResponseDto;
import com.example.pizza.service.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
@Tag(name = "Ingredient", description = "Ingredients management")
public class IngredientController {
    private final IngredientService ingredientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Create new ingredient",
            description = "Create a new ingredient (Amin only)"
           // security = @SecurityRequirement(name = "bearerAuth")
    )
    public IngredientResponseDto createIngredient(@Valid @RequestBody IngredientRequestDto requestDto){
        return ingredientService.createIngredient(requestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Delete ingredient",
            description = "Delete an ingredient by ID (Admin only)"
    )
    public void deleteIngredient(@PathVariable Long id){
        ingredientService.deleteIngredient(id);
    }

    @GetMapping
    @Operation(
            summary = "Get all ingredients"
    )
    public List<IngredientResponseDto> getAllIngredients(){
        return ingredientService.getAllIngredients();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get ingredient by ID")
    public IngredientResponseDto getIngredientById(@PathVariable Long id){
        return ingredientService.getIngredientById(id);
    }

}

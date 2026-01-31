package com.example.pizza.service;

import com.example.pizza.entity.Ingredient;
import com.example.pizza.exception.IngredientAlreadyExistsException;
import com.example.pizza.exception.IngredientNotFoundException;
import com.example.pizza.repository.IngredientRepository;
import com.example.pizza.requestDto.IngredientRequestDto;
import com.example.pizza.responseDto.IngredientResponseDto;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    public IngredientResponseDto createIngredient(IngredientRequestDto requestDto){
        if(ingredientRepository.existsByName(requestDto.name())){
            throw new IngredientAlreadyExistsException(
                    "Ingredient with name '" + requestDto.name() + "' already exists"
            );
        }

        Ingredient ingredient = new Ingredient();
        ingredient.setName(requestDto.name());
        ingredient.setIngredientPrice(requestDto.price());

        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        log.info("Ingredient created with ID {}", savedIngredient.getID());

        return mapToResponseDto(savedIngredient);

    }

    public void deleteIngredient(Long id){
        log.info("Deleting ingredient with ID: {}", id);

        if(!ingredientRepository.existsById(id)){
            throw new IngredientNotFoundException("Ingredient not found with ID: " + id);
        }

        ingredientRepository.deleteById(id);
        log.info("Ingredient with ID {} deleted successfully", id);
    }

    @Transactional(readOnly = true)
    public List<IngredientResponseDto> getAllIngredients(){
    log.debug("Fetching all ingredients");
    return ingredientRepository.findAll()
            .stream()
            .map(this :: mapToResponseDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public IngredientResponseDto getIngredientById(Long id){
        log.debug("Fetching ingredient with ID {}", id);
        Ingredient ingredient = ingredientRepository.findById(id).orElseThrow(()-> new IngredientNotFoundException(
                "Ingredient not found with ID: " + id
        ));
        return mapToResponseDto(ingredient);
    }

    private IngredientResponseDto mapToResponseDto(Ingredient ingredient){
        return new IngredientResponseDto(
                ingredient.getID(),
                ingredient.getName(),
                ingredient.getIngredientPrice()
        );
    }
}

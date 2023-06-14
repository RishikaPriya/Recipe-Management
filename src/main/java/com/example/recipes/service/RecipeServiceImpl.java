package com.example.recipes.service;

import com.example.recipes.controller.exceptions.RecipeNotFoundException;
import com.example.recipes.mapper.RecipeMapper;
import com.example.recipes.model.dto.NutrientValuesWithIngredient;
import com.example.recipes.model.dto.Recipe;
import com.example.recipes.model.entity.IngredientEntity;
import com.example.recipes.model.entity.RecipeEntity;
import com.example.recipes.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final NutrientService nutrientService;
    private final RecipeMapper recipeMapper;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             NutrientService nutrientService,
                             RecipeMapper recipeMapper) {
        this.recipeRepository = recipeRepository;
        this.nutrientService = nutrientService;
        this.recipeMapper = recipeMapper;
    }

    public RecipeEntity createRecipe(Recipe recipeRequest) {
        RecipeEntity recipe = recipeMapper.toEntity(recipeRequest);
        return recipeRepository.save(recipe);
    }

    public RecipeEntity updateRecipe(UUID recipeId, Recipe recipeRequest) {
        RecipeEntity existingRecipe = getRecipeById(recipeId);
        recipeMapper.updateEntityFromDto(recipeRequest, recipeId, existingRecipe);
        return recipeRepository.save(existingRecipe);
    }

    public void deleteRecipe(UUID recipeId) {
        RecipeEntity recipe = getRecipeById(recipeId);
        recipeRepository.delete(recipe);
    }

    public RecipeEntity getRecipeById(UUID recipeId) {
        return recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found with ID: " + recipeId));
    }

    public List<RecipeEntity> getAllRecipes(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return recipeRepository.findAll(pageable).getContent();
    }

    public List<IngredientEntity> getIngredientsByRecipeId(UUID recipeId) {
        RecipeEntity recipe = getRecipeById(recipeId);
        return recipe.getIngredients();
    }

    public List<NutrientValuesWithIngredient> getNutrientValue(UUID recipeId) {
        List<NutrientValuesWithIngredient> nutrientValuesWithIngredients = new ArrayList<>();
        List<IngredientEntity> ingredients = getIngredientsByRecipeId(recipeId);
        for (IngredientEntity ingredient: ingredients) {
            NutrientValuesWithIngredient nutrientValuesWithIngredient = NutrientValuesWithIngredient.builder()
                    .ingredient(ingredient)
                    .nutrientValues(nutrientService.getNutrientValues(ingredient))
                    .build();

            nutrientValuesWithIngredients.add(nutrientValuesWithIngredient);
        }
        return nutrientValuesWithIngredients;
    }
}

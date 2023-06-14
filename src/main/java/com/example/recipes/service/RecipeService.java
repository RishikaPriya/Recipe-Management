package com.example.recipes.service;

import com.example.recipes.model.dto.NutrientValuesWithIngredient;
import com.example.recipes.model.dto.Recipe;
import com.example.recipes.model.entity.IngredientEntity;
import com.example.recipes.model.entity.RecipeEntity;

import java.util.List;
import java.util.UUID;

public interface RecipeService {

    RecipeEntity createRecipe(Recipe recipeRequest);
    RecipeEntity updateRecipe(UUID recipeId, Recipe recipeRequest);
    void deleteRecipe(UUID recipeId);
    RecipeEntity getRecipeById(UUID recipeId);
    List<RecipeEntity> getAllRecipes(int page, int pageSize);
    List<IngredientEntity> getIngredientsByRecipeId(UUID recipeId);
    List<NutrientValuesWithIngredient> getNutrientValue(UUID recipeId);
}

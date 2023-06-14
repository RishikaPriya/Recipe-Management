package com.example.recipes.service;

import com.example.recipes.model.dto.NutrientValue;
import com.example.recipes.model.entity.IngredientEntity;

import java.util.Map;

public interface NutrientService {

    Map<String, NutrientValue> getNutrientValues(IngredientEntity ingredientEntity);
}

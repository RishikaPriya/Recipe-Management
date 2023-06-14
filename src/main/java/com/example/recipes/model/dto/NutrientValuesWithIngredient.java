package com.example.recipes.model.dto;

import com.example.recipes.model.entity.IngredientEntity;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Value;

import java.util.Map;

@Builder
@Getter
public class NutrientValuesWithIngredient {

    IngredientEntity ingredient;
    Map<String, NutrientValue> nutrientValues;

}

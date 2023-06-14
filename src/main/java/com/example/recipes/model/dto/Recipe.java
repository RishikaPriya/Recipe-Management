package com.example.recipes.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class Recipe {
    private String name;
    private String description;
    private int cookingTime;
    private int servingSize;
    private List<Ingredient> ingredients;
    private List<String> instructions;
}

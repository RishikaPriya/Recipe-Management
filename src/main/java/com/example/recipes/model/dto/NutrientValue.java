package com.example.recipes.model.dto;

import lombok.Data;

@Data
public class NutrientValue {
    private String label;
    private double quantity;
    private String unit;
}

package com.example.recipes.controller.exceptions;

public class UnableToParseNutrientServiceResponseException extends RuntimeException {
    public UnableToParseNutrientServiceResponseException(String message) {
        super(message);
    }
}

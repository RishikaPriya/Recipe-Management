package com.example.recipes.controller.exceptions;

public class ErrorFetchingNutrient extends RuntimeException {
    public ErrorFetchingNutrient(String message) {
        super(message);
    }
}

package com.example.recipes.controller;

import com.example.recipes.model.dto.NutrientValuesWithIngredient;
import com.example.recipes.model.dto.Recipe;
import com.example.recipes.model.entity.RecipeEntity;
import com.example.recipes.service.RecipeService;
import com.example.recipes.service.RecipeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping
    public ResponseEntity<RecipeEntity> createRecipe(@RequestBody Recipe recipe) {
        RecipeEntity createdRecipe = recipeService.createRecipe(recipe);
        return new ResponseEntity<>(createdRecipe, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeEntity> updateRecipe(@PathVariable("id") UUID id, @RequestBody Recipe Recipe) {
        RecipeEntity updatedRecipe = recipeService.updateRecipe(id, Recipe);
        return new ResponseEntity<>(updatedRecipe, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable("id") UUID id) {
        recipeService.deleteRecipe(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeEntity> getRecipeById(@PathVariable("id") UUID id) {
        RecipeEntity recipe = recipeService.getRecipeById(id);
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RecipeEntity>> getAllRecipes(@RequestParam(defaultValue = "1") int page,
                                                            @RequestParam(defaultValue = "5") int pageSize) {
        List<RecipeEntity> recipes = recipeService.getAllRecipes(page-1, pageSize);
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @GetMapping("/{id}/nutrients")
    public ResponseEntity<List<NutrientValuesWithIngredient>> getNutrientsForRecipeById(@PathVariable("id") UUID id) {
        List<NutrientValuesWithIngredient> nutrientValuesWithIngredients = recipeService.getNutrientValue(id);
        return new ResponseEntity<>(nutrientValuesWithIngredients, HttpStatus.OK);
    }
}

package com.example.recipes.config;

import com.example.recipes.mapper.RecipeMapper;
import com.example.recipes.repository.RecipeRepository;
import com.example.recipes.service.NutrientService;
import com.example.recipes.service.NutrientServiceImpl;
import com.example.recipes.service.RecipeService;
import com.example.recipes.service.RecipeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

public class ApplicationConfig {

    @Bean
    public NutrientService nutrientService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        return new NutrientServiceImpl(restTemplate, objectMapper);
    }

    @Bean
    public RecipeService recipeService(RecipeRepository recipeRepository,
                                       NutrientService nutrientService,
                                       RecipeMapper recipeMapper) {
        return new RecipeServiceImpl(recipeRepository, nutrientService, recipeMapper);
    }

}

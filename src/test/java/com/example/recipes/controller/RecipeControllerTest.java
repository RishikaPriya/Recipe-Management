package com.example.recipes.controller;

import com.example.recipes.RecipesApplication;
import com.example.recipes.model.entity.RecipeEntity;
import com.example.recipes.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import utils.TestUtils;

import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = RecipesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RecipeControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RecipeService recipeService;

    @Test
    void testAddRecipe() throws IOException {
        ResponseEntity<RecipeEntity> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/recipe",
                        TestUtils.getRecipeInput("src/test/resources/recipeRequest.json"),
                        RecipeEntity.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void testGetRecipeById() throws IOException {
        ResponseEntity<RecipeEntity> saveRecipe = this.restTemplate
                .postForEntity("http://localhost:" + port + "/recipe",
                        TestUtils.getRecipeInput("src/test/resources/recipeRequest.json"),
                        RecipeEntity.class);

        ResponseEntity<RecipeEntity> responseEntity = this.restTemplate
                .getForEntity("http://localhost:" + port + "/recipe/" + Objects.requireNonNull(saveRecipe.getBody()).getId().toString(),
                        RecipeEntity.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void testGetRecipes() throws IOException {
        for (int i = 0; i < 5; i++) {
            this.restTemplate
                    .postForEntity("http://localhost:" + port + "/recipe",
                            TestUtils.getRecipeInput("src/test/resources/recipeRequest.json"),
                            RecipeEntity.class);
        }
        ResponseEntity<RecipeEntity[]> responseEntities = this.restTemplate
                .getForEntity("http://localhost:" + port + "/recipe?page=2", RecipeEntity[].class);

        assertEquals(HttpStatus.OK, responseEntities.getStatusCode());
        assertEquals(2, responseEntities.getBody().length);
    }

    @Test
    void testDeleteRecipes() throws IOException {
        ResponseEntity<RecipeEntity> saveRecipe = this.restTemplate
                .postForEntity("http://localhost:" + port + "/recipe",
                        TestUtils.getRecipeInput("src/test/resources/recipeRequest.json"),
                        RecipeEntity.class);

        String recipeId =  Objects.requireNonNull(saveRecipe.getBody()).getId().toString();

        ResponseEntity<RecipeEntity> responseEntity = this.restTemplate
                .getForEntity("http://localhost:" + port + "/recipe/" + recipeId,
                        RecipeEntity.class);
        assertNotNull(responseEntity.getBody());

        this.restTemplate.delete("http://localhost:" + port + "/recipe/" + recipeId);

        ResponseEntity<String> error = this.restTemplate
                .getForEntity("http://localhost:" + port + "/recipe/" + recipeId, String.class);
        assertEquals("Recipe not found with ID: " + recipeId, error.getBody());
    }
}
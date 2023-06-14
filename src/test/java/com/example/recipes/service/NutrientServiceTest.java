package com.example.recipes.service;

import com.example.recipes.controller.exceptions.ErrorFetchingNutrient;
import com.example.recipes.model.dto.NutrientValue;
import com.example.recipes.model.entity.IngredientEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static utils.TestUtils.getJsonNodeResponse;
import static utils.TestUtils.getNutrientValue;

@ExtendWith(MockitoExtension.class)
class NutrientServiceTest {

    @Mock
    private RestTemplate restTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    private NutrientServiceImpl nutrientService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        nutrientService = new NutrientServiceImpl(restTemplate, objectMapper);
        ReflectionTestUtils.setField(nutrientService, "url", "http://test.com/api");
        ReflectionTestUtils.setField(nutrientService, "appId", "test_app_id");
        ReflectionTestUtils.setField(nutrientService, "appKey", "test_app_key");

    }

    @Test
    void testGetNutrientValuesWithValidResponse() throws IOException {
        IngredientEntity ingredientEntity = new IngredientEntity();
        ingredientEntity.setName("milk");
        ingredientEntity.setQuantity("1 cup");

        ResponseEntity<JsonNode> nutrientResponse = new ResponseEntity<>(
                getJsonNodeResponse("src/test/resources/nutrientResponse.json"), HttpStatus.OK);
        Mockito.when(restTemplate.getForEntity(anyString(), eq(JsonNode.class)))
                .thenReturn(nutrientResponse);

        Map<String, NutrientValue> nutrientValues = nutrientService.getNutrientValues(ingredientEntity);

        assertEquals(getNutrientValue("src/test/resources/expectedNutrientValue.json"), nutrientValues);
    }

    @Test
    void testGetNutrientValuesThrowsException() {
        IngredientEntity ingredientEntity = new IngredientEntity();
        ingredientEntity.setName("");
        ingredientEntity.setQuantity("");

        Mockito.when(restTemplate.getForEntity(anyString(), eq(JsonNode.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing or empty param 'ingr'"));

        assertThrows(ErrorFetchingNutrient.class, () -> nutrientService.getNutrientValues(new IngredientEntity()));
    }

}